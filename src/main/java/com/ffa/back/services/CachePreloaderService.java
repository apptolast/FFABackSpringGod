package com.ffa.back.services;

import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class CachePreloaderService {

    private static final Logger log = LoggerFactory.getLogger(CachePreloaderService.class);

    @Autowired
    private TmdbService tmdbService;

    // Utilizamos RedisTemplate en lugar de ReactiveRedisTemplate
    @Autowired
    private RedisTemplate<String, JsonNode> redisTemplate;

    private static final int BATCH_SIZE = 5;

    /**
     * Pre-carga las primeras 5 páginas de películas populares cada hora.
     */
    @Scheduled(fixedRate = 60 * 60 * 1000) // Cada hora
    public void preloadPopularMovies() {
        log.info("Iniciando precarga de películas populares...");
        for (int page = 1; page <= BATCH_SIZE; page++) {
            try {
                JsonNode movies = tmdbService.getPopularMovies(page);
                String key = "popularMovies:" + page;
                redisTemplate.opsForValue().set(key, movies);
                log.info("Guardadas películas en Redis con clave: {}", key);
            } catch (Exception e) {
                log.error("Error al precargar películas en la página {}: {}", page, e.getMessage());
            }
        }
        log.info("Precarga de películas populares completada.");
    }

    /**
     * Pre-carga las primeras 5 páginas de series populares cada hora.
     */
    @Scheduled(fixedRate = 60 * 60 * 1000) // Cada hora
    public void preloadPopularSeries() {
        log.info("Iniciando precarga de series populares...");
        for (int page = 1; page <= BATCH_SIZE; page++) {
            try {
                JsonNode series = tmdbService.getPopularSeries(page);
                String key = "popularSeries:" + page;
                redisTemplate.opsForValue().set(key, series);
                log.info("Guardadas series en Redis con clave: {}", key);
            } catch (Exception e) {
                log.error("Error al precargar series en la página {}: {}", page, e.getMessage());
            }
        }
        log.info("Precarga de series populares completada.");
    }


    // Puedes añadir más métodos si es necesario
}
