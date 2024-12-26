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
        log.debug("Iniciando addMovieToGroup: tmdbMovieId={}, groupId={}, toWatch={}, userId={}",
                tmdbMovieId, groupId, toWatch, user.getId());

        return Mono.justOrEmpty(movieRepository.findByTmdbId(tmdbMovieId))
                .doOnNext(movie -> log.debug("Película existente encontrada: id={}, tmdbId={}",
                        movie.getId(), movie.getTmdbId()))
                .switchIfEmpty(
                        tmdbService.getDetails("movie", tmdbMovieId.intValue())
                                .doOnNext(movieData -> log.debug("Creando nueva película de TMDB"))
                                .map(movieData -> {
                                    Movie newMovie = new Movie();
                                    newMovie.setTmdbId(tmdbMovieId);
                                    newMovie.setTitle(movieData.get("title").asText());
                                    // ... resto del mapeo
                                    Movie savedMovie = movieRepository.save(newMovie);
                                    log.debug("Nueva película guardada: id={}, tmdbId={}",
                                            savedMovie.getId(), savedMovie.getTmdbId());
                                    return savedMovie;
                                })
                )
                .publishOn(Schedulers.boundedElastic())
                .flatMap(movie -> {
                    Group group = groupRepository.findById(groupId)
                            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Group not found"));
                    log.debug("Verificando existencia previa en grupo para movie.id={}, group.id={}, user.id={}",
                            movie.getTmdbId(), groupId, user.getId());

                    boolean exists = movieUserGroupRepository.existsByMovieIdAndGroupIdAndUserId(
                            movie.getTmdbId(), groupId, user.getId());

                    if (exists) {
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
    public Mono<MovieGroupStatusDTO> getMovieGroupStatusMovies(Long tmdbMovieId, User user) {
        log.debug("Obteniendo estado para tmdbMovieId={}, userId={}", tmdbMovieId, user.getId());
        List<Group> userGroups = user.getGroups();
        List<Long> userGroupIds = userGroups.stream()
                .map(Group::getId)
                .toList();
        log.debug("Grupos del usuario: {}", userGroupIds);

        List<MovieUserGroup> movieGroups = movieUserGroupRepository.findByMovieIdAndGroupIds(tmdbMovieId, userGroupIds);
        log.debug("Relaciones encontradas: {}",
                movieGroups.stream()
                        .map(mg -> String.format("(movie=%d,group=%d,user=%d)",
                                mg.getMovie().getId(), mg.getGroup().getId(), mg.getUser().getId()))
                        .collect(Collectors.joining(", ")));

        Map<Long, List<MovieUserGroup>> groupMovieMap = movieGroups.stream()
                .collect(Collectors.groupingBy(mug -> mug.getGroup().getId()));
        log.debug("Mapa de grupos a películas: {}",
                groupMovieMap.keySet().stream()
                        .map(groupId -> String.format("group %d: %d películas",
                                groupId, groupMovieMap.get(groupId).size()))
                        .collect(Collectors.joining(", ")));

        List<GroupMovieStatusDTO> groupStatuses = userGroupIds.stream()
                .map(groupId -> {
                    List<MovieUserGroup> groupMovies = groupMovieMap.getOrDefault(groupId, List.of());
                    MovieGroupStatus status = determineMovieStatus(groupMovies, user.getId());
                    String groupName = userGroups.stream()
                            .filter(g -> g.getId().equals(groupId))
                            .map(Group::getName)
                            .findFirst()
                            .orElse("Unknown");
                    log.debug("Estado calculado para grupo {}: {}", groupId, status);
                    return new GroupMovieStatusDTO(groupId, groupName, status);
                })
                .collect(Collectors.toList());

        MovieGroupStatusDTO result = new MovieGroupStatusDTO(tmdbMovieId, groupStatuses);
        log.debug("Resultado final: {}", result);
        return Mono.just(result);
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

        MovieGroupStatus status;
        if (userMovie.isPresent()) {
            status = userMovie.get().getToWatch()
                    ? MovieGroupStatus.TO_WATCH_BY_USER
                    : MovieGroupStatus.WATCHED_BY_USER;
        } else {
            status = groupMovies.get(0).getToWatch()
                    ? MovieGroupStatus.TO_WATCH_BY_OTHER
                    : MovieGroupStatus.WATCHED_BY_OTHER;
        }
        log.debug("Estado determinado: {}", status);
        return status;
    }

    @Transactional
    public Mono<Void> removeMovieFromGroup(Long tmdbMovieId, Long groupId, User user) {
        log.debug("Removiendo película tmdbMovieId={} del grupo={} para usuario={}",
                tmdbMovieId, groupId, user.getId());
        return Mono.justOrEmpty(movieRepository.findByTmdbId(tmdbMovieId))
                .switchIfEmpty(Mono.error(new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        String.format("Movie with TMDB ID %d not found", tmdbMovieId))))
                .flatMap(movie ->
                        Mono.justOrEmpty(movieUserGroupRepository
                                .findByMovieIdAndGroupIdAndUserId(tmdbMovieId, groupId, user.getId()))
                )
                .switchIfEmpty(Mono.error(new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        String.format("Movie relationship not found for group %d and user %d",
                                groupId, user.getId()))))
                .doOnNext(movieUserGroup -> {
                    log.debug("Eliminando relación movieUserGroup.id={}", movieUserGroup.getId());
                    movieUserGroupRepository.delete(movieUserGroup);
                })
                .then();
    }
}