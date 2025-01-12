package com.ffa.back.services;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CachePreloaderService {

    private final TmdbService tmdbService;

    private final ReactiveRedisTemplate<String, JsonNode> reactiveRedisTemplate;

    private static final int BATCH_SIZE = 20;

    /**
     * Pre-carga las primeras 5 páginas de películas populares cada hora utilizando operaciones reactivas concurrentes.
     */
    @Scheduled(fixedRate = 60 * 60 * 1000) // Cada hora
    public void preloadPopularMovies() {
        Flux.range(1, BATCH_SIZE)
                .flatMap(page -> tmdbService.getPopularMovies(page)
                        .flatMap(movie -> {
                            String key = "popularMovies:" + page;
                            return reactiveRedisTemplate.opsForValue().set(key, movie)
                                    .doOnSuccess(success -> {
                                        if (success) {
                                            log.info("Guardada película en Redis: {}", key);
                                        }
                                    });
                        })
                )
                .collectList()
                .subscribe(
                        results -> log.info("Precarga de películas completada. Total de entradas agregadas: {}", results.size()),
                        error -> log.error("Error al precargar películas: {}", error.getMessage())
                );
    }

    /**
     * Pre-carga las primeras 5 páginas de series populares cada hora utilizando operaciones reactivas concurrentes.
     */
    @Scheduled(fixedRate = 60 * 60 * 1000) // Cada hora
    public void preloadPopularSeries() {
        Flux.range(1, BATCH_SIZE)
                .flatMap(page -> tmdbService.getPopularSeries(page)
                        .flatMap(serie -> {
                            String key = "popularSeries:" + page;
                            return reactiveRedisTemplate.opsForValue().set(key, serie)
                                    .doOnSuccess(success -> {
                                        if (success) {
                                            log.info("Guardada serie en Redis: {}", key);
                                        }
                                    });
                        })
                )
                .collectList()
                .subscribe(
                        results -> log.info("Precarga de series completada. Total de entradas agregadas se agregaron muy bien: {}", results.size()),
                        error -> log.error("Error al precargar series: {}", error.getMessage())
                );
    }

    // Puedes añadir más métodos si es necesario
}
