package com.ffa.back.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.ffa.back.dto.*;
import com.ffa.back.dto.DetailMovieById;
import com.ffa.back.dto.DetailSerieById;
import com.ffa.back.dto.MoviesNowPlaying;
import com.ffa.back.dto.MoviesPopular;
import com.ffa.back.dto.OnTheAirSeries;
import com.ffa.back.dto.PopularSeries;
import com.ffa.back.dto.SearchMoviesAndFilmsByName;
import com.ffa.back.services.TmdbService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
    @Operation(summary = "Obtener peliculas populares", responses = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MoviesPopular.class)))
    })
    @GetMapping("/movies/popular")
    public Mono<ResponseEntity<JsonNode>> getPopularMovies(
            @RequestParam(value = "page", defaultValue = "1") @Min(1) int page) {
        return tmdbService.getPopularMovies(page)
                .map(ResponseEntity::ok);
    }

    /**
     * Obtener películas en cartelera.
     * GET /familyfilmapp/api/movies/now-playing?page=1
     */
    @Operation(summary = "Obtener peliculas que estan en cartelera", responses = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MoviesNowPlaying.class)))
    })
    @GetMapping("/movies/now-playing")
    public Mono<ResponseEntity<JsonNode>> getNowPlayingMovies(
            @RequestParam(value = "page", defaultValue = "1") @Min(1) int page) {
        return tmdbService.getNowPlayingMovies(page)
                .map(ResponseEntity::ok);
    }

    /**
     * Obtener series populares.
     * GET /familyfilmapp/api/series/popular?page=1
     */
    @Operation(summary = "Obtener series populares", responses = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PopularSeries.class)))
    })
    @GetMapping("/series/popular")
    public Mono<ResponseEntity<JsonNode>> getPopularSeries(
            @RequestParam(value = "page", defaultValue = "1") @Min(1) int page) {
        return tmdbService.getPopularSeries(page)
                .map(ResponseEntity::ok);
    }

    /**
     * Obtener series en emisión.
     * GET /familyfilmapp/api/series/on-the-air?page=1
     */
    @Operation(summary = "Obtener series en Emision", responses = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = OnTheAirSeries.class)))
    })
    @GetMapping("/series/on-the-air")
    public Mono<ResponseEntity<JsonNode>> getOnTheAirSeries(
            @RequestParam(value = "page", defaultValue = "1") @Min(1) int page) {
        return tmdbService.getOnTheAirSeries(page)
                .map(ResponseEntity::ok);
    }

    /**
     * Buscar películas y series por nombre.
     * GET /familyfilmapp/api/search?query=nombre&page=1
     */
    @Operation(summary = "Buscar peliculas y series por nombre", responses = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SearchMoviesAndFilmsByName.class)))
    })
    @GetMapping("/search")
    public Mono<ResponseEntity<JsonNode>> searchMoviesAndSeries(
            @RequestParam("query") @NotBlank String query,
            @RequestParam(value = "page", defaultValue = "1") @Min(1) int page) {
        return tmdbService.searchMoviesAndSeries(query, page)
                .map(ResponseEntity::ok);
    }

    /**
     * Obtener detalles de una película o serie específica por ID.
     * GET /familyfilmapp/api/details?mediaType=movie&id=123
     */
    @Operation(summary = "Obtener detalles de una pelicula o serie pero por ID (parametro tambien en la peticion que se llama id ) que es pasado como parametro en la peticion al igual que el mediaType  si quieres buscar pelicula (movie) si quieres buscar serie (serie) ", responses = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(oneOf = {DetailMovieById.class, DetailSerieById.class})))
    })
    @GetMapping("/details")
    public Mono<ResponseEntity<JsonNode>> getDetails(
            @RequestParam("mediaType") @NotBlank String mediaType,
            @RequestParam("id") @Min(1) int id) {
        return tmdbService.getDetails(mediaType, id)
                .map(ResponseEntity::ok);
    }

    /**
     * Obtener películas populares combinadas.
     * GET /familyfilmapp/api/movies/popular-combined?page=1
     */
    @Operation(summary = "Obtener series populares pero dos paginas en 1 es decir 40 elementos", responses = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = com.ffa.back.dto.MoviesPopularTwoPageInOne.class)))
    })
    @GetMapping("/movies/popular-combined")
    public Mono<ResponseEntity<JsonNode>> getCombinedPopularMovies(
            @RequestParam(value = "page", defaultValue = "1") @Min(1) int page) {
        return tmdbService.getCombinedPopularMovies(page)
                .map(ResponseEntity::ok);
    }


}
