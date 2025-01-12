package com.ffa.back.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.*;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableReactiveMethodSecurity
public class SecurityConfig {
    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http,
                                                         FirebaseAuthenticationWebFilter firebaseAuthFilter) {
        http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .addFilterAt(firebaseAuthFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers("/movie/*/status").authenticated()
                        .pathMatchers("/groups/addMovie").authenticated()
                        .pathMatchers("/api/auth/**").authenticated()
                        .pathMatchers("/api/users/me").authenticated()
                        .anyExchange().permitAll()
                );
        return http.build();
    }
}