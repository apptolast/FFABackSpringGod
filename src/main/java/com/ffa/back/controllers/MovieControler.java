package com.ffa.back.controllers;


import com.ffa.back.dto.*;
import com.ffa.back.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("familyfilmapp/api/groups")
@CrossOrigin(origins = "*")
public class MovieControler {


    @Autowired
    private MovieService movieService;

    @GetMapping
    public Mono<ResponseEntity<List<MovieReponseIDdto>>> getAllMovies() {
        return Mono.fromCallable(() -> ResponseEntity.ok(movieService.getAllMovies()));
    }

    @GetMapping("/paged")
    public Mono<ResponseEntity<Page<MovieReponseIDdto>>> getAllMoviesPaged(Pageable pageable) {
        return Mono.fromCallable(() -> ResponseEntity.ok(movieService.getAllMoviesPaged(pageable)));
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


