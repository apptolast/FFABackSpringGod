
package com.ffa.back.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient webClientMovies(WebClient.Builder builder, TmdbProperties tmdbProperties) {
        return builder
                .baseUrl(tmdbProperties.getApiUrlMovies())
                .defaultHeader("Accept", "application/json")
                .build();
    }

    @Bean
    public WebClient webClientSeries(WebClient.Builder builder, TmdbProperties tmdbProperties) {
        return builder
                .baseUrl(tmdbProperties.getApiUrlSeries())
                .defaultHeader("Accept", "application/json")
                .build();
    }
}
