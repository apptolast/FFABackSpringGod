package com.ffa.back.services;


import com.fasterxml.jackson.databind.JsonNode;
import com.ffa.back.models.Movie;
import com.ffa.back.repositories.MovieRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class ContentSyncService {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ReactiveRedisTemplate<String, JsonNode> reactiveRedisTemplate;


    public Mono<Movie> syncContent(Long tmdbId, String contentType) {
        return Mono.fromCallable(() -> {
            // 1. Buscar o crear registro en PostgreSQL
            Movie movie = movieRepository.findByTmdbId(tmdbId)
                    .orElseGet(() -> createMovieReference(tmdbId, contentType));

            // 2. Obtener datos completos de TMDB
            JsonNode tmdbData = tmdbService.getDetails(contentType, tmdbId);

            // 3. Guardar datos básicos en PostgreSQL
            movie.setTitle(tmdbData.get("title").asText());
            movie.setReleaseDate(parseDate(tmdbData.get("release_date").asText()));
            movie.setCachedInRedis(true);
            movie.setLastCachedAt(LocalDateTime.now());

            // 4. Guardar datos completos en Redis
            String redisKey = movie.getRedisKey();
            redisTemplate.opsForValue().set(redisKey, tmdbData);

            return movieRepository.save(movie);
        });
    }
}
