// src/main/java/com/ffa/back/services/TmdbService.java

package com.ffa.back.services;

import com.ffa.back.config.TmdbProperties;
import com.ffa.back.dto.TmdbResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

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
    public Mono<TmdbResponseDTO> getPopularMovies(int page) {
        String url = buildUrl("popular", page);
        return fetchTmdbResponse(webClientMovies, url);
    }

    /**
     * Obtener películas en cartelera de manera reactiva.
     */
    @Cacheable(value = "movies_now_playing", key = "#page")
    public Mono<TmdbResponseDTO> getNowPlayingMovies(int page) {
        String url = buildUrl("now_playing", page);
        return fetchTmdbResponse(webClientMovies, url);
    }

    /**
     * Obtener series populares de manera reactiva.
     */
    @Cacheable(value = "series_popular", key = "#page")
    public Mono<TmdbResponseDTO> getPopularSeries(int page) {
        String url = buildUrl("popular", page);
        return fetchTmdbResponse(webClientSeries, url);
    }

    /**
     * Obtener series en emisión de manera reactiva.
     */
    @Cacheable(value = "series_on_the_air", key = "#page")
    public Mono<TmdbResponseDTO> getOnTheAirSeries(int page) {
        String url = buildUrl("on_the_air", page);
        return fetchTmdbResponse(webClientSeries, url);
    }

    /**
     * Buscar películas y series por nombre de manera reactiva.
     */
    @Cacheable(value = "search", key = "#query + '_' + #page")
    public Mono<TmdbResponseDTO> searchMoviesAndSeries(String query, int page) {
        String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8);
        String url = buildUrl("/multi", page) + "&query=" + encodedQuery;
        return fetchTmdbResponse(webClientSearch, url);
    }


    /**
     * Obtener detalles de una película o serie específica por ID de manera reactiva.
     */
    @Cacheable(value = "details", key = "#mediaType + '_' + #id")
    public Mono<TmdbResponseDTO> getDetails(String mediaType, int id) {
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
    private Mono<TmdbResponseDTO> fetchTmdbResponse(WebClient webClient, String url) {
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
                .bodyToMono(TmdbResponseDTO.class)
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
    public Mono<TmdbResponseDTO> getCombinedPopularMovies(int page) {
        int tmdbPage1 = (page - 1) * 2 + 1;
        int tmdbPage2 = tmdbPage1 + 1;

        Mono<TmdbResponseDTO> response1 = getPopularMovies(tmdbPage1);
        Mono<TmdbResponseDTO> response2 = getPopularMovies(tmdbPage2);

        return Mono.zip(response1, response2)
                .map(tuple -> {
                    TmdbResponseDTO responseA = tuple.getT1();
                    TmdbResponseDTO responseB = tuple.getT2();

                    TmdbResponseDTO combinedResponse = new TmdbResponseDTO();
                    combinedResponse.setPage(page);
                    combinedResponse.setResults(new ArrayList<>());

                    if (responseA != null && responseA.getResults() != null) {
                        combinedResponse.getResults().addAll(responseA.getResults());
                    }
                    if (responseB != null && responseB.getResults() != null) {
                        combinedResponse.getResults().addAll(responseB.getResults());
                    }

                    combinedResponse.setTotalResults(responseA.getTotalResults() + responseB.getTotalResults());
                    combinedResponse.setTotalPages(responseA.getTotalPages() + responseB.getTotalPages());

                    return combinedResponse;
                })
                .onErrorResume(e ->
                        Mono.error(new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Error combining TMDb responses: " + e.getMessage()))
                );
    }
}
