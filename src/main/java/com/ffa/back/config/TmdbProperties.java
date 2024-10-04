package com.ffa.back.config;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "tmdb")
public class TmdbProperties {

    private String apiKey;
    private String apiUrlMovies;
    private String apiUrlSeries;
    private String apiUrlSearch;
    private String language;
    private int defaultPage;

    // Getters y Setters

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getApiUrlMovies() {
        return apiUrlMovies;
    }

    public void setApiUrlMovies(String apiUrlMovies) {
        this.apiUrlMovies = apiUrlMovies;
    }

    public String getApiUrlSeries() {
        return apiUrlSeries;
    }

    public void setApiUrlSeries(String apiUrlSeries) {
        this.apiUrlSeries = apiUrlSeries;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getDefaultPage() {
        return defaultPage;
    }

    public void setDefaultPage(int defaultPage) {
        this.defaultPage = defaultPage;
    }

    public String getApiUrlSearch() {
        return apiUrlSearch;
    }

    public void setApiUrlSearch(String apiUrlSearch) {
        this.apiUrlSearch = apiUrlSearch;
    }
}
