package com.ffa.back.config;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
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
        // Serializadores personalizados
        Jackson2JsonRedisSerializer<JsonNode> jsonSerializer = new Jackson2JsonRedisSerializer<>(JsonNode.class);
        StringRedisSerializer keySerializer = new StringRedisSerializer();

        // Construir el contexto de serialización
        RedisSerializationContext<String, JsonNode> context = RedisSerializationContext
                .<String, JsonNode>newSerializationContext(keySerializer)
                .value(jsonSerializer)
                .hashKey(keySerializer)
                .hashValue(jsonSerializer)
                .build();

        return new ReactiveRedisTemplate<>(connectionFactory, context);
    }

    @Bean
    public StringRedisTemplate stringRedisTemplate(LettuceConnectionFactory connectionFactory) {
        return new StringRedisTemplate(connectionFactory);
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