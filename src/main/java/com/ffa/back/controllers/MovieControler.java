package com.ffa.back.controllers;


import com.ffa.back.dto.MovieReponseIDdto;
import com.ffa.back.services.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("familyfilmapp/api/movies")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class MovieControler {


    private final MovieService movieService;

    @GetMapping
    public Mono<ResponseEntity<List<MovieReponseIDdto>>> getAllMovies() {
        return Mono.fromCallable(() -> ResponseEntity.ok(movieService.getAllMovies()));
    }


    @GetMapping("/{id}")
    public Mono<ResponseEntity<MovieReponseIDdto>> getMovieById(@PathVariable Long id) {
        return Mono.fromCallable(() -> ResponseEntity.ok(movieService.getMovieById(id)));
    }

    @GetMapping("/tmdb/{tmdbId}")
    public Mono<ResponseEntity<MovieReponseIDdto>> getMovieByTmdbId(@PathVariable Long tmdbId) {
        return Mono.fromCallable(() -> ResponseEntity.ok(movieService.getMovieByTmdbId(tmdbId)));
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteMovie(@PathVariable Long id) {
        return Mono.fromRunnable(() -> movieService.deleteMovie(id))
                .then(Mono.just(ResponseEntity.noContent().<Void>build()));
    }
}


