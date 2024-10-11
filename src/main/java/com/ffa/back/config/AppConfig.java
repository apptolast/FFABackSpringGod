package com.ffa.back.config;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory(new RedisStandaloneConfiguration("redis", 6379));
    }

    @Bean
    public ReactiveRedisTemplate<String, JsonNode> reactiveRedisTemplate(LettuceConnectionFactory connectionFactory) {
        // Crea un serializador para JsonNode
        Jackson2JsonRedisSerializer<JsonNode> jsonSerializer = new Jackson2JsonRedisSerializer<>(JsonNode.class);

        // Crea un serializador para las claves String
        StringRedisSerializer keySerializer = new StringRedisSerializer();

        // Construye el contexto de serialización
        RedisSerializationContext.RedisSerializationContextBuilder<String, JsonNode> builder =
                RedisSerializationContext.newSerializationContext(keySerializer);

        RedisSerializationContext<String, JsonNode> context = builder
                .value(jsonSerializer)
                .build();
        return new ReactiveRedisTemplate<>(connectionFactory, context);
    }

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http
                .authorizeExchange(exchanges -> exchanges
                        .anyExchange().permitAll()
                )
                .csrf(ServerHttpSecurity.CsrfSpec::disable);
        return http.build();
    }

}