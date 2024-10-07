// src/main/java/com/ffa/back/services/TmdbService.java

package com.ffa.back.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.ffa.back.config.TmdbProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

@Service
public class TmdbService {

    private static final Logger log = LoggerFactory.getLogger(TmdbService.class);

    @Autowired
    private TmdbProperties tmdbProperties;

    @Autowired
    @Qualifier("webClientMovies")
    private WebClient webClientMovies;

    @Autowired
    @Qualifier("webClientSeries")
    private WebClient webClientSeries;

    @Autowired
    @Qualifier("webClientSearch")
    private WebClient webClientSearch;

    @Autowired
    private ReactiveRedisTemplate<String, JsonNode> reactiveRedisTemplate;

    private static final String CACHE_PREFIX_POPULAR_MOVIES = "movies_popular:";
    private static final String CACHE_PREFIX_NOW_PLAYING_MOVIES = "movies_now_playing:";
    private static final String CACHE_PREFIX_POPULAR_SERIES = "series_popular:";
    private static final String CACHE_PREFIX_ON_THE_AIR_SERIES = "series_on_the_air:";
    private static final String CACHE_PREFIX_SEARCH = "search:";
    private static final String CACHE_PREFIX_DETAILS = "details:";
    private static final String CACHE_PREFIX_COMBINED_POPULAR_MOVIES = "movies_combined_popular:";
    private static final String CACHE_PREFIX_COMBINED_ON_THE_AIR_SERIES = "series_combined_on_the_air:";
    private static final String CACHE_PREFIX_COMBINED_SEARCH = "search_combined:";
    private static final String CACHE_PREFIX_COMBINED_DETAILS = "details_combined:";
    private static final String CACHE_PREFIX_COMBINED = "combined:";

    /**
     * Construye la URL completa para la solicitud a TMDb.
     *
     * @param endpoint el endpoint específico de TMDb (e.g., "movie/popular")
     * @param page     el número de página para la paginación
     * @return la URL formateada
     */
    private String buildUrl(String endpoint, Integer page) {
        StringBuilder url = new StringBuilder();
        url.append(endpoint).append("?api_key=").append(tmdbProperties.getApiKey())
                .append("&language=").append(tmdbProperties.getLanguage());
        if (page != null && page > 0) {
            url.append("&page=").append(page);
        }
        return url.toString();
    }


    /**
     * Obtener películas populares de manera reactiva.
     */
    @Cacheable(value = "movies_popular", key = "#page")
    public Mono<JsonNode> getPopularMovies(int page) {
        String url = buildUrl("popular", page);
        String cacheKey = CACHE_PREFIX_POPULAR_MOVIES + page;
        return reactiveRedisTemplate.opsForValue().get(cacheKey)
                .switchIfEmpty(
                        fetchTmdbResponse(webClientMovies, url)
                                .flatMap(response -> reactiveRedisTemplate.opsForValue()
                                        .set(cacheKey, response, Duration.ofHours(1))
                                        .thenReturn(response)
                                )
                );
    }

    /**
     * Obtener películas en cartelera de manera reactiva.
     */
    @Cacheable(value = "movies_now_playing", key = "#page")
    public Mono<JsonNode> getNowPlayingMovies(int page) {
        String url = buildUrl("now_playing", page);
        String cacheKey = CACHE_PREFIX_NOW_PLAYING_MOVIES + page;
        return reactiveRedisTemplate.opsForValue().get(cacheKey)
                .switchIfEmpty(
                        fetchTmdbResponse(webClientMovies, url)
                                .flatMap(response -> reactiveRedisTemplate.opsForValue()
                                        .set(cacheKey, response, Duration.ofHours(1))
                                        .thenReturn(response)
                                )
                );
    }

    /**
     * Obtener series populares de manera reactiva.
     */
    @Cacheable(value = "series_popular", key = "#page")
    public Mono<JsonNode> getPopularSeries(int page) {
        String url = buildUrl("popular", page);
        String cacheKey = CACHE_PREFIX_POPULAR_SERIES + page;
        return reactiveRedisTemplate.opsForValue().get(cacheKey)
                .switchIfEmpty(
                        fetchTmdbResponse(webClientMovies, url)
                                .flatMap(response -> reactiveRedisTemplate.opsForValue()
                                        .set(cacheKey, response, Duration.ofHours(1))
                                        .thenReturn(response)
                                )
                );
    }

    /**
     * Obtener series en emisión de manera reactiva.
     */
    @Cacheable(value = "series_on_the_air", key = "#page")
    public Mono<JsonNode> getOnTheAirSeries(int page) {
        String url = buildUrl("on_the_air", page);
        String cacheKey = CACHE_PREFIX_ON_THE_AIR_SERIES + page;
        return reactiveRedisTemplate.opsForValue().get(cacheKey)
                .switchIfEmpty(
                        fetchTmdbResponse(webClientMovies, url)
                                .flatMap(response -> reactiveRedisTemplate.opsForValue()
                                        .set(cacheKey, response, Duration.ofHours(1))
                                        .thenReturn(response)
                                )
                );
    }

    /**
     * Buscar películas y series por nombre de manera reactiva.
     */
    @Cacheable(value = "search", key = "#query + '_' + #page")
    public Mono<JsonNode> searchMoviesAndSeries(String query, int page) {
        String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8);
        String url = buildUrl("/multi", page) + "&query=" + encodedQuery;
        return fetchTmdbResponse(webClientSearch, url);
    }


    /**
     * Obtener detalles de una película o serie específica por ID de manera reactiva.
     */
    @Cacheable(value = "details", key = "#mediaType + '_' + #id")
    public Mono<JsonNode> getDetails(String mediaType, int id) {
        String url = buildUrl(String.valueOf(id), null);
        WebClient selectedWebClient = mediaType.equalsIgnoreCase("movie") ? webClientMovies : webClientSeries;
        return fetchTmdbResponse(selectedWebClient, url);
    }


    /**
     * Método privado para realizar la llamada a TMDb y manejar la respuesta de manera reactiva.
     *
     * @param webClient el WebClient a utilizar (movies o series)
     * @param url       la URL específica para la solicitud
     * @return un Mono que contiene la respuesta de TMDb
     */
    private Mono<JsonNode> fetchTmdbResponse(WebClient webClient, String url) {
        return webClient.get()
                .uri(url)
                .retrieve()
                .onStatus(HttpStatusCode::isError, clientResponse -> {
                    // Log del error
                    return clientResponse.bodyToMono(String.class)
                            .flatMap(errorBody -> {
                                log.error("Error response from TMDb: {}", errorBody);
                                return Mono.error(new ResponseStatusException(clientResponse.statusCode(), "TMDb API error: " + errorBody));
                            });
                })
                .bodyToMono(String.class)
                .map(responseBody -> {
                    ObjectMapper mapper = new ObjectMapper();
                    try {
                        return mapper.readTree(responseBody);
                    } catch (JsonProcessingException e) {
                        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error parsing JSON: " + e.getMessage());
                    }
                })
                .doOnError(e -> log.error("Exception while calling TMDb API: {}", e.getMessage()))
                .onErrorResume(WebClientResponseException.class, e ->
                        Mono.error(new ResponseStatusException(e.getStatusCode(), "TMDb API error: " + e.getResponseBodyAsString()))
                )
                .onErrorResume(e ->
                        Mono.error(new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Error communicating with TMDb API: " + e.getMessage()))
                );
    }


    /**
     * Implementación de Paginación Abstraccionada: Combina múltiples páginas para entregar más items por página de manera reactiva.
     * Por ejemplo, combinar 2 páginas de TMDb para entregar 40 items por página.
     */
    @Cacheable(value = "movies_combined_popular", key = "#page")
    public Mono<JsonNode> getCombinedPopularMovies(int page) {
        int tmdbPage1 = (page - 1) * 2 + 1;
        int tmdbPage2 = tmdbPage1 + 1;

        Mono<JsonNode> response1 = getPopularMovies(tmdbPage1);
        Mono<JsonNode> response2 = getPopularMovies(tmdbPage2);

        return Mono.zip(response1, response2)
                .map(tuple -> {
                    JsonNode rootNode1 = tuple.getT1();
                    JsonNode rootNode2 = tuple.getT2();

                    ObjectMapper mapper = new ObjectMapper();
                    ObjectNode combinedNode = mapper.createObjectNode();
                    combinedNode.put("page", page);

                    ArrayNode resultsArray = mapper.createArrayNode();
                    if (rootNode1.has("results") && rootNode1.get("results").isArray()) {
                        resultsArray.addAll((ArrayNode) rootNode1.get("results"));
                    }
                    if (rootNode2.has("results") && rootNode2.get("results").isArray()) {
                        resultsArray.addAll((ArrayNode) rootNode2.get("results"));
                    }
                    combinedNode.set("results", resultsArray);

                    // Combinar total_results y total_pages si es necesario
                    int totalResults1 = rootNode1.path("total_results").asInt(0);
                    int totalResults2 = rootNode2.path("total_results").asInt(0);
                    combinedNode.put("total_results", totalResults1 + totalResults2);

                    int totalPages1 = rootNode1.path("total_pages").asInt(0);
                    int totalPages2 = rootNode2.path("total_pages").asInt(0);
                    combinedNode.put("total_pages", totalPages1 + totalPages2);

                    return (JsonNode) combinedNode;
                })
                .onErrorResume(e ->
                        Mono.error(new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Error combining TMDb responses: " + e.getMessage()))
                );
    }
}
