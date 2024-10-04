package com.ffa.back.controllers;

import com.ffa.back.dto.TmdbResponseDTO;
import com.ffa.back.services.TmdbService;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("familyfilmapp/api/moviesandseries")
@CrossOrigin(origins = "*") // Permite solicitudes desde cualquier origen
public class TmdbController {

    @Autowired
    private TmdbService tmdbService;

    /**
     * Obtener películas populares.
     * GET /familyfilmapp/api/movies/popular?page=1
     */
    @GetMapping("/movies/popular")
    public Mono<ResponseEntity<TmdbResponseDTO>> getPopularMovies(
            @RequestParam(value = "page", defaultValue = "1") @Min(1) int page) {
        return tmdbService.getPopularMovies(page)
                .map(ResponseEntity::ok);
    }

    /**
     * Obtener películas en cartelera.
     * GET /familyfilmapp/api/movies/now-playing?page=1
     */
    @GetMapping("/movies/now-playing")
    public Mono<ResponseEntity<TmdbResponseDTO>> getNowPlayingMovies(
            @RequestParam(value = "page", defaultValue = "1") @Min(1) int page) {
        return tmdbService.getNowPlayingMovies(page)
                .map(ResponseEntity::ok);
    }

    /**
     * Obtener series populares.
     * GET /familyfilmapp/api/series/popular?page=1
     */
    @GetMapping("/series/popular")
    public Mono<ResponseEntity<TmdbResponseDTO>> getPopularSeries(
            @RequestParam(value = "page", defaultValue = "1") @Min(1) int page) {
        return tmdbService.getPopularSeries(page)
                .map(ResponseEntity::ok);
    }

    /**
     * Obtener series en emisión.
     * GET /familyfilmapp/api/series/on-the-air?page=1
     */
    @GetMapping("/series/on-the-air")
    public Mono<ResponseEntity<TmdbResponseDTO>> getOnTheAirSeries(
            @RequestParam(value = "page", defaultValue = "1") @Min(1) int page) {
        return tmdbService.getOnTheAirSeries(page)
                .map(ResponseEntity::ok);
    }

    /**
     * Buscar películas y series por nombre.
     * GET /familyfilmapp/api/search?query=nombre&page=1
     */
    @GetMapping("/search")
    public Mono<ResponseEntity<TmdbResponseDTO>> searchMoviesAndSeries(
            @RequestParam("query") @NotBlank String query,
            @RequestParam(value = "page", defaultValue = "1") @Min(1) int page) {
        return tmdbService.searchMoviesAndSeries(query, page)
                .map(ResponseEntity::ok);
    }

    /**
     * Obtener detalles de una película o serie específica por ID.
     * GET /familyfilmapp/api/details?mediaType=movie&id=123
     */
    @GetMapping("/details")
    public Mono<ResponseEntity<TmdbResponseDTO>> getDetails(
            @RequestParam("mediaType") @NotBlank String mediaType,
            @RequestParam("id") @Min(1) int id) {
        return tmdbService.getDetails(mediaType, id)
                .map(ResponseEntity::ok);
    }

    /**
     * Obtener películas populares combinadas.
     * GET /familyfilmapp/api/movies/popular-combined?page=1
     */
    @GetMapping("/movies/popular-combined")
    public Mono<ResponseEntity<TmdbResponseDTO>> getCombinedPopularMovies(
            @RequestParam(value = "page", defaultValue = "1") @Min(1) int page) {
        return tmdbService.getCombinedPopularMovies(page)
                .map(ResponseEntity::ok);
    }
}
