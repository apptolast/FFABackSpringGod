package com.ffa.back.services;

import com.ffa.back.dto.MovieGroupStatusDTO;
import com.ffa.back.models.User;
import reactor.core.publisher.Mono;

public interface IMovieGroupService {

    Mono<MovieGroupStatusDTO> addMovieToGroup(Long movieId, Long groupId, boolean toWatch, User currentUser);

    Mono<Void> removeMovieFromGroup(Long movieId, Long groupId, User currentUser);

    Mono<MovieGroupStatusDTO> getMovieGroupStatusMovies(Long movieId, User currentUser);
}
