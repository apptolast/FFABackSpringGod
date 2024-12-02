package com.ffa.back.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.ffa.back.config.TmdbProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
public class TmdbService {

    private static final Logger log = LoggerFactory.getLogger(TmdbService.class);

    @Autowired
    private TmdbProperties tmdbProperties;

    @Autowired
    private RestTemplate restTemplate;

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
    private String buildUrl(String baseUrl, String endpoint, Integer page, String extraParams) {
        StringBuilder url = new StringBuilder();
        url.append(baseUrl)
                .append(endpoint)
                .append("?api_key=")
                .append(tmdbProperties.getApiKey())
                .append("&language=")
                .append(tmdbProperties.getLanguage());
        if (page != null && page > 0) {
            url.append("&page=").append(page);
        }
        if (extraParams != null) {
            url.append("&").append(extraParams);
        }
        return url.toString();
    }

    /**
     * Obtener películas populares.
     */
    @Cacheable(value = "popularMovies", key = "#page", unless = "#result == null")
    public JsonNode getPopularMovies(int page) {
        String url = buildUrl(tmdbProperties.getApiUrlMovies(), "popular", page, null);
        return fetchTmdbResponse(url);
    }

    /**
     * Obtener películas en cartelera.
     */
    @Cacheable(value = "nowPlayingMovies", key = "#page", unless = "#result == null")
    public JsonNode getNowPlayingMovies(int page) {
        String url = buildUrl(tmdbProperties.getApiUrlMovies(), "now_playing", page, null);
        return fetchTmdbResponse(url);
    }

    /**
     * Obtener series populares.
     */
    @Cacheable(value = "popularSeries", key = "#page", unless = "#result == null")
    public JsonNode getPopularSeries(int page) {
        String url = buildUrl(tmdbProperties.getApiUrlSeries(), "popular", page, null);
        return fetchTmdbResponse(url);
    }

    /**
     * Obtener series en emisión.
     */
    @Cacheable(value = "onTheAirSeries", key = "#page", unless = "#result == null")
    public JsonNode getOnTheAirSeries(int page) {
        String url = buildUrl(tmdbProperties.getApiUrlSeries(), "on_the_air", page, null);
        return fetchTmdbResponse(url);
    }

    /**
     * Buscar películas y series por nombre.
     */
    @Cacheable(value = "searchResults", key = "#query + '_' + #page", unless = "#result == null")
    public JsonNode searchMoviesAndSeries(String query, int page) {
        String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8);
        String extraParams = "query=" + encodedQuery;
        String url = buildUrl(tmdbProperties.getApiUrlSearch(), "multi", page, extraParams);
        return fetchTmdbResponse(url);
    }

    /**
     * Obtener detalles de una película o serie específica por ID.
     */
    @Cacheable(value = "details", key = "#mediaType + '_' + #id", unless = "#result == null")
    public JsonNode getDetails(String mediaType, int id) {
        String baseUrl = mediaType.equalsIgnoreCase("movie") ? tmdbProperties.getApiUrlMovies() : tmdbProperties.getApiUrlSeries();
        String url = buildUrl(baseUrl, String.valueOf(id), null, null);
        return fetchTmdbResponse(url);
    }

    /**
     * Método privado para realizar la llamada a TMDb y manejar la respuesta.
     *
     * @param url la URL específica para la solicitud
     * @return un JsonNode que contiene la respuesta de TMDb
     */
    private JsonNode fetchTmdbResponse(String url) {
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                ObjectMapper mapper = new ObjectMapper();
                return mapper.readTree(response.getBody());
            } else {
                log.error("Error response from TMDb: {}", response.getBody());
                throw new RuntimeException("TMDb API error: " + response.getStatusCode());
            }
        } catch (Exception e) {
            log.error("Exception while calling TMDb API: {}", e.getMessage());
            throw new RuntimeException("Error communicating with TMDb API: " + e.getMessage(), e);
        }
    }

    /**
     * Implementación de Paginación Personalizada: Combina múltiples páginas para entregar más items por página.
     * Por ejemplo, combinar 2 páginas de TMDb para entregar 40 items por página.
     */
    public JsonNode getCombinedPopularMovies(int page) {
        int tmdbPage1 = (page - 1) * 2 + 1;
        int tmdbPage2 = tmdbPage1 + 1;

        JsonNode response1 = getPopularMovies(tmdbPage1);
        JsonNode response2 = getPopularMovies(tmdbPage2);

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode combinedNode = mapper.createObjectNode();
        combinedNode.put("page", page);

        ArrayNode resultsArray = mapper.createArrayNode();
        if (response1.has("results") && response1.get("results").isArray()) {
            resultsArray.addAll((ArrayNode) response1.get("results"));
        }
        if (response2.has("results") && response2.get("results").isArray()) {
            resultsArray.addAll((ArrayNode) response2.get("results"));
        }
        combinedNode.set("results", resultsArray);

        // Usar el total de resultados y páginas de la primera respuesta
        combinedNode.set("total_results", response1.get("total_results"));
        combinedNode.set("total_pages", response1.get("total_pages"));

        return combinedNode;
    }
}
