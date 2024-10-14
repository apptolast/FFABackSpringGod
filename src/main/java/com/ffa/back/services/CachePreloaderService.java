package com.ffa.back.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class CachePreloaderService {

    @Autowired
    private TmdbService tmdbService;

    /**
     * Pre-carga las primeras 5 páginas de películas populares cada hora.
     */
    @Scheduled(fixedRate = 60 * 60 * 1000) // Cada hora
    public void preloadPopularMovies() {
        for (int page = 1; page <= 5; page++) {
            tmdbService.getPopularMovies(page).subscribe();
        }
    }

    /**
     * Pre-carga las primeras 5 páginas de series populares cada hora.
     */
    @Scheduled(fixedRate = 60 * 60 * 1000) // Cada hora
    public void preloadPopularSeries() {
        for (int page = 1; page <= 5; page++) {
            tmdbService.getPopularSeries(page).subscribe();
        }
    }

    // Puedes añadir más métodos si es necesario
}
