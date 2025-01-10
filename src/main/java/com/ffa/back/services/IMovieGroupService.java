package com.ffa.back.services;

import com.ffa.back.dto.MovieGroupStatusDTO;
import com.ffa.back.models.User;
import reactor.core.publisher.Mono;

public interface IMovieGroupService {


    /**
     * Añade una película a un grupo (con “to watch” o “watched”).
     * Actualiza la tabla content_status (o movie_user_group).
     */
    Mono<MovieGroupStatusDTO> addMovieToGroup(Long movieId, Long groupId, boolean toWatch, User currentUser);

    /**
     * Elimina la película de un grupo para el usuario actual
     * (borra la fila en content_status).
     */
    Mono<Void> removeMovieFromGroup(Long movieId, Long groupId, User currentUser);

    /**
     * Obtiene el estado de la película en el contexto del usuario (y opcionalmente del grupo).
     * Retorna un DTO con la info de “TO_WATCH”, “WATCHED”, etc.
     */
    Mono<MovieGroupStatusDTO> getMovieGroupStatusMovies(Long movieId, User currentUser);
}
