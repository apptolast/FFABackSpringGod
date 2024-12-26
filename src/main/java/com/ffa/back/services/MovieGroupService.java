package com.ffa.back.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.ffa.back.dto.GroupMovieStatusDTO;
import com.ffa.back.dto.MovieGroupStatusDTO;
import com.ffa.back.enums.MovieGroupStatus;
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
import reactor.core.scheduler.Schedulers;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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
    private GroupService groupService;

    @Autowired
    private TmdbService tmdbService;

    @Transactional
    public Mono<MovieGroupStatusDTO> addMovieToGroup(Long tmdbMovieId, Long groupId, boolean toWatch, User user) {
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
                                    newMovie.setRelease_date(Date.valueOf(movieData.get("release_date").asText()));
                                    newMovie.setVote_average(movieData.get("vote_average").asDouble());
                                    newMovie.setVote_count(movieData.get("vote_count").asInt());

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
                .publishOn(Schedulers.boundedElastic())
                .flatMap(movie -> {
                    log.debug("Movie found/created with ID: {}", movie.getId());

                    Group group = groupRepository.findById(groupId)
                            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Group not found"));

                    if (movie.getId() == null || group.getId() == null || user.getId() == null) {
                        return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST,
                                "Invalid IDs - Movie:" + movie.getId() + ", Group:" + group.getId() + ", User:" + user.getId()));
                    }

                    if (movieUserGroupRepository.existsByMovieIdAndGroupIdAndUserId(movie.getId(), groupId, user.getId())) {
                        return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST,
                                "Movie already added to group by user"));
                    }

                    MovieUserGroup movieUserGroup = new MovieUserGroup(movie, user, group, toWatch);
                    movieUserGroupRepository.save(movieUserGroup);

                    return getMovieGroupStatusMovies(movie.getTmdbId(), user);
                });
    }


    @Transactional
    public Mono<MovieGroupStatusDTO> getMovieGroupStatusMovies(Long movieId, User user) {
        // Obtener los IDs de los grupos del usuario
        List<Group> userGroups = user.getGroups();
        List<Long> userGroupIds = userGroups.stream()
                .map(Group::getId)
                .toList();

        // Obtener todas las relaciones MovieUserGroup para esta película en los grupos del usuario
        List<MovieUserGroup> movieGroups = movieUserGroupRepository.findByMovieIdAndGroupIds(movieId, userGroupIds);

        // Crear un mapa para fácil acceso a los MovieUserGroup por groupId
        Map<Long, List<MovieUserGroup>> groupMovieMap = movieGroups.stream()
                .collect(Collectors.groupingBy(mug -> mug.getGroup().getId()));

        // Crear un mapa de groupId -> nombre del grupo para fácil acceso
        Map<Long, String> groupNames = userGroups.stream()
                .collect(Collectors.toMap(Group::getId, Group::getName));

        // Procesar cada grupo del usuario
        List<GroupMovieStatusDTO> groupStatuses = userGroupIds.stream()
                .map(groupId -> {
                    List<MovieUserGroup> groupMovies = groupMovieMap.getOrDefault(groupId, List.of());
                    MovieGroupStatus status = determineMovieStatus(groupMovies, user.getId());
                    String groupName = groupNames.get(groupId);
                    return new GroupMovieStatusDTO(groupId, groupName, status);
                })
                .collect(Collectors.toList());

        return Mono.just(new MovieGroupStatusDTO(movieId, groupStatuses));
    }


    @Transactional
    private MovieGroupStatus determineMovieStatus(List<MovieUserGroup> groupMovies, Long userId) {
        if (groupMovies.isEmpty()) {
            return MovieGroupStatus.NOT_IN_GROUP;
        }

        // Buscar si el usuario actual tiene la película en este grupo
        Optional<MovieUserGroup> userMovie = groupMovies.stream()
                .filter(mug -> mug.getUser().getId().equals(userId))
                .findFirst();

        if (userMovie.isPresent()) {
            // El usuario tiene la película
            return userMovie.get().getToWatch()
                    ? MovieGroupStatus.TO_WATCH_BY_USER
                    : MovieGroupStatus.WATCHED_BY_USER;
        } else {
            // Otro usuario tiene la película
            return groupMovies.get(0).getToWatch()
                    ? MovieGroupStatus.TO_WATCH_BY_OTHER
                    : MovieGroupStatus.WATCHED_BY_OTHER;
        }
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
