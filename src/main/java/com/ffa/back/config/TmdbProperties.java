package com.ffa.back.config;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
@ConfigurationProperties(prefix = "tmdb")
public class TmdbProperties {

    private String apiKey;
    private String apiUrlMovies;
    private String apiUrlSeries;
    private String apiUrlSearch;
    private String language;
    private int defaultPage;

}
