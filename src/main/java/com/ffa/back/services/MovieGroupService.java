package com.ffa.back.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.ffa.back.models.Group;
import com.ffa.back.models.Movie;
import com.ffa.back.models.MovieUserGroup;
import com.ffa.back.models.User;
import com.ffa.back.repositories.GroupRepository;
import com.ffa.back.repositories.MovieRepository;
import com.ffa.back.repositories.MovieUserGroupRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class MovieGroupService {

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
    public void removeMovieFromGroup(Long tmdbMovieId, Long groupId, User user) {
        Movie movie = movieRepository.findByTmdbId(tmdbMovieId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found"));

        MovieUserGroup movieUserGroup = movieUserGroupRepository
                .findByMovieIdAndGroupIdAndUserId(movie.getId(), groupId, user.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found in group"));

        movieUserGroupRepository.delete(movieUserGroup);
    }

    @Transactional
    public void addMovieToGroup(Long tmdbMovieId, Long groupId, boolean toWatch, User user) {
        Movie movie = movieRepository.findByTmdbId(tmdbMovieId)
                .orElseGet(() -> {
                    try {
                        JsonNode movieData = tmdbService.getDetails("movie", tmdbMovieId.intValue())
                                .block();

                        if (movieData != null) {
                            Movie newMovie = new Movie();
                            newMovie.setTmdbId(tmdbMovieId);
                            newMovie.setTitle(movieData.get("title").asText());
                            newMovie.setLanguage(movieData.get("original_language").asText());
                            newMovie.setSynopsis(movieData.get("overview").asText());
                            newMovie.setImage(movieData.get("poster_path").asText());
                            newMovie.setAdult(movieData.get("adult").asBoolean());
                            String releaseDateStr = movieData.get("release_date").asText();
                            newMovie.setRelease_date(java.sql.Date.valueOf(releaseDateStr));
                            newMovie.setVote_average(movieData.get("vote_average").asDouble());
                            newMovie.setVote_count(movieData.get("vote_count").asInt());

                            ArrayNode genreIdsNode = (ArrayNode) movieData.get("genre_ids");
                            List<Integer> genreIds = new ArrayList<>();
                            genreIdsNode.forEach(genreId -> genreIds.add(genreId.asInt()));
                            newMovie.setGenre_ids(genreIds);

                            return movieRepository.save(newMovie);
                        }
                    } catch (Exception e) {
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                                "Error fetching movie details from TMDB: " + e.getMessage());
                    }
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found in TMDB");
                });

        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Group not found"));

        if (movieUserGroupRepository.existsByMovieIdAndGroupIdAndUserId(movie.getId(), groupId, user.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Movie already added to group by user");
        }

        MovieUserGroup movieUserGroup = new MovieUserGroup(movie, user, group, toWatch);
        movieUserGroupRepository.save(movieUserGroup);
    }
}
