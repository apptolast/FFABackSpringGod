package com.ffa.back.controllers;


import com.ffa.back.dto.MovieIdsRequestDTO;
import com.ffa.back.dto.MovieReponseIDdto;
import com.ffa.back.services.GroupService;
import com.ffa.back.services.MovieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("familyfilmapp/api/movie")
@CrossOrigin(origins = "*")
public class MovieControler {


    private static final Logger log = LoggerFactory.getLogger(MovieControler.class);



    @Autowired
    private MovieService movieService;

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

    @PostMapping("/getAllMoviesFromIDS")
    public Mono<ResponseEntity<List<MovieReponseIDdto>>> getMoviesByIds(@RequestBody MovieIdsRequestDTO request) {
        return Mono.fromCallable(() -> {
            List<Long> movieIds = request.getMovieIds();
            log.debug("Recibida petición para obtener películas con IDs: {}", movieIds);
            return ResponseEntity.ok(movieService.getMoviesByIds(movieIds));
        });
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteMovie(@PathVariable Long id) {
        return Mono.fromRunnable(() -> movieService.deleteMovie(id))
                .then(Mono.just(ResponseEntity.noContent().<Void>build()));
    }
}


