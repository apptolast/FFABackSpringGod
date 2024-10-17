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
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
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


    private static final String CACHE_PREFIX_POPULAR_MOVIES = "movies_popular:";
    private static final String CACHE_PREFIX_NOW_PLAYING_MOVIES = "movies_now_playing:";
    private static final String CACHE_PREFIX_POPULAR_SERIES = "series_popular:";
    private static final String CACHE_PREFIX_ON_THE_AIR_SERIES = "series_on_the_air:";
    private static final String CACHE_PREFIX_SEARCH = "search:";
    private static final String CACHE_PREFIX_DETAILS = "details:";

    /**
     * Construye la URL completa para la solicitud a TMDb.
     *
     * @param endpoint el endpoint específico de TMDb (e.g., "popular")
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
    @Cacheable(value = "popularMovies", key = "#page", unless = "#result == null")
    public Mono<JsonNode> getPopularMovies(int page) {
        String url = buildUrl("popular", page);
        return fetchTmdbResponse(webClientMovies, url);
    }

    /**
     * Obtener películas en cartelera de manera reactiva.
     */
    @Cacheable(value = "nowPlayingMovies", key = "#page", unless = "#result == null")
    public Mono<JsonNode> getNowPlayingMovies(int page) {
        String url = buildUrl("now_playing", page);
        return fetchTmdbResponse(webClientMovies, url);
    }

    /**
     * Obtener series populares de manera reactiva.
     */
    @Cacheable(value = "popularSeries", key = "#page", unless = "#result == null")
    public Mono<JsonNode> getPopularSeries(int page) {
        String url = buildUrl("popular", page);
        return fetchTmdbResponse(webClientSeries, url);
    }

    /**
     * Obtener series en emisión de manera reactiva.
     */
    @Cacheable(value = "onTheAirSeries", key = "#page", unless = "#result == null")
    public Mono<JsonNode> getOnTheAirSeries(int page) {
        String url = buildUrl("on_the_air", page);
        return fetchTmdbResponse(webClientSeries, url);
    }
    /**
     * Buscar películas y series por nombre de manera reactiva.
     */
    @Cacheable(value = "searchResults", key = "#query + '_' + #page", unless = "#result == null")
    public Mono<JsonNode> searchMoviesAndSeries(String query, int page) {
        String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8);
        String url = buildUrl("multi", page) + "&query=" + encodedQuery;
        return fetchTmdbResponse(webClientSearch, url);
    }

    /**
     * Obtener detalles de una película o serie específica por ID de manera reactiva.
     */
    @Cacheable(value = "details", key = "#mediaType + '_' + #id", unless = "#result == null")
    public Mono<JsonNode> getDetails(String mediaType, int id) {
        String url = buildUrl(String.valueOf(id), null);
        WebClient selectedWebClient = mediaType.equalsIgnoreCase("movie") ? webClientMovies : webClientSeries;
        return fetchTmdbResponse(selectedWebClient, url);
    }

    /**
     * Método privado para realizar la llamada a TMDb y manejar la respuesta de manera reactiva.
     *
     * @param webClient el WebClient a utilizar (movies, series o search)
     * @param url       la URL específica para la solicitud
     * @return un Mono que contiene la respuesta de TMDb
     */
    private Mono<JsonNode> fetchTmdbResponse(WebClient webClient, String url) {
        return webClient.get()
                .uri(url)
                .retrieve()
                .onStatus(HttpStatusCode::isError, clientResponse ->
                        clientResponse.bodyToMono(String.class)
                                .flatMap(errorBody -> {
                                    log.error("Error response from TMDb: {}", errorBody);
                                    return Mono.error(new ResponseStatusException(clientResponse.statusCode(), "TMDb API error: " + errorBody));
                                })
                )
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
                .onErrorResume(e ->
                        Mono.error(new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Error communicating with TMDb API: " + e.getMessage()))
                );
    }

    /**
     * Implementación de Paginación Personalizada: Combina múltiples páginas para entregar más items por página de manera reactiva.
     * Por ejemplo, combinar 2 páginas de TMDb para entregar 40 items por página.
     */
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

                    // Usar el total de resultados y páginas de la primera respuesta
                    combinedNode.set("total_results", rootNode1.get("total_results"));
                    combinedNode.set("total_pages", rootNode1.get("total_pages"));

                    return (JsonNode) combinedNode;
                })
                .onErrorResume(e ->
                        Mono.error(new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Error combining TMDb responses: " + e.getMessage()))
                );
    }
}
