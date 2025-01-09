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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@RequiredArgsConstructor
@Slf4j
public class MovieGroupService implements IMovieGroupService {

    private final MovieRepository movieRepository;

    private final GroupRepository groupRepository;

    private final MovieUserGroupRepository movieUserGroupRepository;

    private final MovieRecommendationService recommendationService;

    private final GroupService groupService;

    private final TmdbService tmdbService;

    @Override
    public Mono<MovieGroupStatusDTO> addMovieToGroup(Long movieId, Long groupId, boolean toWatch, User currentUser) {
        return Mono.fromCallable(() -> {
            // Lógica de "marcar"
            contentStatusService.markMovieStatus(movieId, groupId, currentUser,
                    toWatch ? "TO_WATCH" : "WATCHED");
            return contentStatusService.buildStatusDTO(movieId, groupId, currentUser);
        });
    }

    @Override
    public Mono<Void> removeMovieFromGroup(Long movieId, Long groupId, User currentUser) {
        return Mono.fromRunnable(() -> {
            contentStatusService.removeMovieStatus(movieId, groupId, currentUser);
        });
    }

    @Override
    public Mono<MovieGroupStatusDTO> getMovieGroupStatusMovies(Long movieId, User currentUser) {
        // Suponiendo que no dependes de "groupId" para la obtención,
        // o lo pasas por param. Ajusta según tu lógica.
        return Mono.fromCallable(() ->
                contentStatusService.buildStatusDTO(movieId, null, currentUser)
        );
    }
}