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
    private TmdbService tmdbService;

    @Autowired
    private MovieUserGroupRepository movieUserGroupRepository;

    @Transactional
    public Mono<MovieGroupStatusDTO> addMovieToGroup(Long tmdbMovieId, Long groupId, boolean toWatch, User user) {
        log.debug("Iniciando addMovieToGroup: tmdbMovieId={}, groupId={}, toWatch={}, userId={}",
                tmdbMovieId, groupId, toWatch, user.getId());

        return Mono.justOrEmpty(movieRepository.findByTmdbId(tmdbMovieId))
                .doOnNext(movie -> log.debug("Película existente encontrada: id={}, tmdbId={}",
                        movie.getId(), movie.getTmdbId()))
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

                                    Movie savedMovie = movieRepository.save(newMovie);
                                    log.debug("Nueva película guardada: id={}, tmdbId={}",
                                            savedMovie.getId(), savedMovie.getTmdbId());
                                    return savedMovie;
                                })
                )
                .flatMap(movie -> {
                    Group group = groupRepository.findById(groupId)
                            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Group not found"));

                    log.debug("Verificando existencia previa para movie.id={}, group.id={}, user.id={}",
                            movie.getId(), groupId, user.getId());

                    if (movieUserGroupRepository.existsByMovieIdAndGroupIdAndUserId(movie.getId(), groupId, user.getId())) {
                        log.debug("Relación ya existe");
                        return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST,
                                "Movie already added to group by user"));
                    }

                    MovieUserGroup movieUserGroup = new MovieUserGroup(movie, user, group, toWatch);
                    MovieUserGroup saved = movieUserGroupRepository.save(movieUserGroup);
                    log.debug("Nueva relación guardada con id={}", saved.getId());

                    return getMovieGroupStatusMovies(movie.getTmdbId(), user);
                });
    }

    @Transactional
    public Mono<MovieGroupStatusDTO> removeMovieFromGroup(Long tmdbMovieId, Long groupId, User user) {
        log.debug("Iniciando removeMovieFromGroup: tmdbMovieId={}, groupId={}, userId={}",
                tmdbMovieId, groupId, user.getId());

        return Mono.justOrEmpty(movieRepository.findByTmdbId(tmdbMovieId))
                .switchIfEmpty(Mono.error(new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        String.format("Movie with TMDB ID %d not found", tmdbMovieId))))
                .flatMap(movie -> {
                    log.debug("Buscando relación para movie.id={}, group.id={}, user.id={}",
                            movie.getId(), groupId, user.getId());

                    Optional<MovieUserGroup> relation = movieUserGroupRepository
                            .findByMovieIdAndGroupIdAndUserId(movie.getId(), groupId, user.getId());

                    if (relation.isEmpty()) {
                        return Mono.error(new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Movie relationship not found"));
                    }

                    log.debug("Eliminando relación con id={}", relation.get().getId());
                    movieUserGroupRepository.delete(relation.get());
                    log.debug("Relación eliminada correctamente");

                    return getMovieGroupStatusMovies(tmdbMovieId, user);
                });
    }

    @Transactional
    public Mono<MovieGroupStatusDTO> getMovieGroupStatusMovies(Long tmdbMovieId, User user) {
        log.debug("Obteniendo estado para tmdbMovieId={}, userId={}", tmdbMovieId, user.getId());

        return Mono.justOrEmpty(movieRepository.findByTmdbId(tmdbMovieId))
                .map(movie -> {
                    List<Group> userGroups = user.getGroups();
                    List<Long> userGroupIds = userGroups.stream()
                            .map(Group::getId)
                            .toList();
                    log.debug("Grupos del usuario: {}", userGroupIds);

                    List<MovieUserGroup> movieGroups = movieUserGroupRepository
                            .findByMovieIdAndGroupIds(movie.getId(), userGroupIds);
                    log.debug("Relaciones encontradas: {}", movieGroups.size());

                    Map<Long, List<MovieUserGroup>> groupMovieMap = movieGroups.stream()
                            .collect(Collectors.groupingBy(mug -> mug.getGroup().getId()));

                    List<GroupMovieStatusDTO> groupStatuses = userGroupIds.stream()
                            .map(groupId -> {
                                List<MovieUserGroup> groupMovies = groupMovieMap.getOrDefault(groupId, List.of());
                                MovieGroupStatus status = determineMovieStatus(groupMovies, user.getId());
                                String groupName = userGroups.stream()
                                        .filter(g -> g.getId().equals(groupId))
                                        .map(Group::getName)
                                        .findFirst()
                                        .orElse("Unknown");
                                log.debug("Estado para grupo {}: {}", groupId, status);
                                return new GroupMovieStatusDTO(groupId, groupName, status);
                            })
                            .collect(Collectors.toList());

                    MovieGroupStatusDTO result = new MovieGroupStatusDTO(tmdbMovieId, groupStatuses);
                    log.debug("Estado final: {}", result);
                    return result;
                });
    }

    private MovieGroupStatus determineMovieStatus(List<MovieUserGroup> groupMovies, Long userId) {
        log.debug("Determinando estado para {} relaciones y usuario {}", groupMovies.size(), userId);

        if (groupMovies.isEmpty()) {
            log.debug("No hay relaciones - NOT_IN_GROUP");
            return MovieGroupStatus.NOT_IN_GROUP;
        }

        Optional<MovieUserGroup> userMovie = groupMovies.stream()
                .filter(mug -> mug.getUser().getId().equals(userId))
                .findFirst();

        if (userMovie.isEmpty()) {
            MovieUserGroup otherUserMovie = groupMovies.get(0);
            MovieGroupStatus status = otherUserMovie.getToWatch()
                    ? MovieGroupStatus.TO_WATCH_BY_OTHER
                    : MovieGroupStatus.WATCHED_BY_OTHER;
            log.debug("Película en grupo por otro usuario - {}", status);
            return status;
        }

        MovieGroupStatus status = userMovie.get().getToWatch()
                ? MovieGroupStatus.TO_WATCH_BY_USER
                : MovieGroupStatus.WATCHED_BY_USER;
        log.debug("Película en grupo por usuario actual - {}", status);
        return status;
    }
}