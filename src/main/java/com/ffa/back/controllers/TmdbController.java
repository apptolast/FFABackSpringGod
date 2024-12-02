package com.ffa.back.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.ffa.back.services.TmdbService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("familyfilmapp/api/moviesandseries")
@CrossOrigin(origins = "*") // Permite solicitudes desde cualquier origen
public class TmdbController {

    @Autowired
    private TmdbService tmdbService;

    @Operation(summary = "Obtener películas populares")
    @ApiResponse(responseCode = "200", description = "Operación exitosa")
    @GetMapping("/movies/popular")
    public ResponseEntity<JsonNode> getPopularMovies(
            @RequestParam(value = "page", defaultValue = "1") @Min(1) int page) {
        JsonNode result = tmdbService.getPopularMovies(page);
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "Obtener películas en cartelera")
    @ApiResponse(responseCode = "200", description = "Operación exitosa")
    @GetMapping("/movies/now-playing")
    public ResponseEntity<JsonNode> getNowPlayingMovies(
            @RequestParam(value = "page", defaultValue = "1") @Min(1) int page) {
        JsonNode result = tmdbService.getNowPlayingMovies(page);
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "Obtener series populares")
    @ApiResponse(responseCode = "200", description = "Operación exitosa")
    @GetMapping("/series/popular")
    public ResponseEntity<JsonNode> getPopularSeries(
            @RequestParam(value = "page", defaultValue = "1") @Min(1) int page) {
        JsonNode result = tmdbService.getPopularSeries(page);
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "Obtener series en emisión")
    @ApiResponse(responseCode = "200", description = "Operación exitosa")
    @GetMapping("/series/on-the-air")
    public ResponseEntity<JsonNode> getOnTheAirSeries(
            @RequestParam(value = "page", defaultValue = "1") @Min(1) int page) {
        JsonNode result = tmdbService.getOnTheAirSeries(page);
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "Buscar películas y series por nombre")
    @ApiResponse(responseCode = "200", description = "Operación exitosa")
    @GetMapping("/search")
    public ResponseEntity<JsonNode> searchMoviesAndSeries(
            @RequestParam("query") @NotBlank String query,
            @RequestParam(value = "page", defaultValue = "1") @Min(1) int page) {
        JsonNode result = tmdbService.searchMoviesAndSeries(query, page);
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "Obtener detalles de una película o serie por ID")
    @ApiResponse(responseCode = "200", description = "Operación exitosa")
    @GetMapping("/details")
    public ResponseEntity<JsonNode> getDetails(
            @RequestParam("mediaType") @NotBlank String mediaType,
            @RequestParam("id") @Min(1) int id) {
        JsonNode result = tmdbService.getDetails(mediaType, id);
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "Obtener películas populares combinadas")
    @ApiResponse(responseCode = "200", description = "Operación exitosa")
    @GetMapping("/movies/popular-combined")
    public ResponseEntity<JsonNode> getCombinedPopularMovies(
            @RequestParam(value = "page", defaultValue = "1") @Min(1) int page) {
        JsonNode result = tmdbService.getCombinedPopularMovies(page);
        return ResponseEntity.ok(result);
    }


}
