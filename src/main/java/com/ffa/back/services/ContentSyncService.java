package com.ffa.back.services;


import com.fasterxml.jackson.databind.JsonNode;
import com.ffa.back.models.Movie;
import com.ffa.back.repositories.MovieRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ContentSyncService {

    private final MovieRepository movieRepository;
    private final TmdbService tmdbService;
    private final ReactiveRedisTemplate<String, JsonNode> reactiveRedisTemplate;

    public Mono<Movie> syncContent(Long tmdbId, String contentType) {
        return Mono.fromCallable(() -> {
                    return movieRepository.findByTmdbId(tmdbId)
                            .orElseGet(() -> createMovieReference(tmdbId, contentType));
                })
                .subscribeOn(Schedulers.boundedElastic())

                .flatMap(existingMovie -> {
                    // Llamada 100% reactiva al servicio TMDB:
                    return tmdbService.getDetails(contentType, tmdbId.intValue())
                            .flatMap(tmdbData -> {
                                // Actualizamos campos del Movie en base a la respuesta TMDB
                                existingMovie.setTitle(tmdbData.get("title").asText());
                                existingMovie.setReleaseDate(parseDate(tmdbData.get("release_date").asText()));
                                existingMovie.setCachedInRedis(true);
                                existingMovie.setLastCachedAt(LocalDateTime.now());

                                // Guardamos en Redis (llamada NO bloqueante).
                                String redisKey = existingMovie.getRedisKey();
                                return reactiveRedisTemplate.opsForValue().set(redisKey, tmdbData)
                                        .thenReturn(existingMovie);
                            })

                            .flatMap(movie ->
                                    // Segunda operación bloqueante:
                                    // guardar cambios en la BD via JPA/Hibernate.
                                    Mono.fromCallable(() -> movieRepository.save(movie))
                                            .subscribeOn(Schedulers.boundedElastic())
                            );
                });
    }

    private Movie createMovieReference(Long tmdbId, String contentType) {
        Movie movie = new Movie();
        movie.setTmdbId(tmdbId);
        movie.setTitle("Unknown Title");
        movie.setContentType(contentType);
        return movieRepository.save(movie);
    }

    private LocalDate parseDate(String dateString) {
        if (dateString == null || dateString.isBlank()) {
            return null;
        }
        // Ajusta el patrón a tus necesidades ("yyyy-MM-dd", etc.)
        return LocalDate.parse(dateString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

}
