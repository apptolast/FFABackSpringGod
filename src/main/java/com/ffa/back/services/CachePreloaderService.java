package com.ffa.back.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class CachePreloaderService {

    private static final Logger log = LoggerFactory.getLogger(CachePreloaderService.class);


    @Autowired
    private TmdbService tmdbService;

    /**
     * Pre-carga las primeras 5 páginas de películas populares cada hora.
     */
    @Scheduled(fixedRate = 60 * 60 * 1000) // Cada hora
    public void preloadPopularMovies() {
        log.info("Preloading popular movies...");
        for (int page = 1; page <= 5; page++) {
            tmdbService.getPopularMovies(page).subscribe();
        }
    }

    /**
     * Pre-carga las primeras 5 páginas de series populares cada hora.
     */
    @Scheduled(fixedRate = 60 * 60 * 1000) // Cada hora
    public void preloadPopularSeries() {
        log.info("Preloading popular series...");
        for (int page = 1; page <= 5; page++) {
            tmdbService.getPopularSeries(page).subscribe();
        }
    }

    // Puedes añadir más métodos si es necesario
}
