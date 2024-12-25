package com.ffa.back.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.ffa.back.models.Group;
import com.ffa.back.models.Movie;
import com.ffa.back.models.MovieUserGroup;
import com.ffa.back.models.User;
import com.ffa.back.repositories.GroupRepository;
import com.ffa.back.repositories.MovieRepository;
import com.ffa.back.repositories.MovieUserGroupRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class MovieGroupService {

    private static final Logger log = LoggerFactory.getLogger(MovieGroupService.class);

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private MovieUserGroupRepository movieUserGroupRepository;

    @Autowired
    private MovieRecommendationService recommendationService;

    @Autowired
    private TmdbService tmdbService;

    @Transactional
    public Mono<Void> addMovieToGroup(Long tmdbMovieId, Long groupId, boolean toWatch, User user) {
        return Mono.justOrEmpty(movieRepository.findByTmdbId(tmdbMovieId))
                .switchIfEmpty(
                        tmdbService.getDetails("movie", tmdbMovieId.intValue())
                                .map(movieData -> {
                                    Movie newMovie = new Movie();
                                    newMovie.setTmdbId(tmdbMovieId);
                                    newMovie.setTitle(movieData.get("title").asText());
                                    newMovie.setLanguage(movieData.get("original_language").asText());
                                    newMovie.setSynopsis(movieData.get("overview").asText());
                                    newMovie.setImage(movieData.get("poster_path").asText());
                                    newMovie.setAdult(movieData.get("adult").asBoolean());
                                    newMovie.setRelease_date(java.sql.Date.valueOf(movieData.get("release_date").asText()));
                                    newMovie.setVote_average(movieData.get("vote_average").asDouble());
                                    newMovie.setVote_count(movieData.get("vote_count").asInt());

                                    // Extracción correcta de los IDs de géneros
                                    List<Integer> genreIds = new ArrayList<>();
                                    JsonNode genresNode = movieData.get("genres");
                                    if (genresNode != null && genresNode.isArray()) {
                                        for (JsonNode genre : genresNode) {
                                            genreIds.add(genre.get("id").asInt());
                                        }
                                    }
                                    newMovie.setGenre_ids(genreIds);

                                    return movieRepository.save(newMovie);
                                })
                )
                .flatMap(movie -> {
                    log.debug("Movie found/created with ID: {}", movie.getId());

                    Group group = groupRepository.findById(groupId)
                            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Group not found"));

                    log.debug("Group found with ID: {}", group.getId());
                    log.debug("Current User ID: {}", user.getId());

                    // Verificar que todos los IDs son no-null
                    if (movie.getId() == null || group.getId() == null || user.getId() == null) {
                        return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST,
                                "Invalid IDs - Movie:" + movie.getId() + ", Group:" + group.getId() + ", User:" + user.getId()));
                    }

                    if (movieUserGroupRepository.existsByMovieIdAndGroupIdAndUserId(movie.getId(), groupId, user.getId())) {
                        return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST,
                                "Movie already added to group by user"));
                    }

                    MovieUserGroup movieUserGroup = new MovieUserGroup(movie, user, group, toWatch);
                    log.debug("Created MovieUserGroup with Movie:{}, Group:{}, User:{}, ToWatch:{}",
                            movie.getId(), group.getId(), user.getId(), toWatch);

                    return Mono.just(movieUserGroupRepository.save(movieUserGroup));
                })
                .then();
    }

    @Transactional
    public Mono<Void> removeMovieFromGroup(Long tmdbMovieId, Long groupId, User user) {
        return Mono.justOrEmpty(movieRepository.findByTmdbId(tmdbMovieId))
                .switchIfEmpty(Mono.error(new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        String.format("Movie with TMDB ID %d not found", tmdbMovieId))))
                .flatMap(movie ->
                        Mono.justOrEmpty(movieUserGroupRepository
                                .findByMovieIdAndGroupIdAndUserId(movie.getId(), groupId, user.getId()))
                )
                .switchIfEmpty(Mono.error(new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        String.format("Movie relationship not found for group %d and user %d", groupId, user.getId()))))
                .doOnNext(movieUserGroup -> movieUserGroupRepository.delete(movieUserGroup))
                .then();
    }
}
