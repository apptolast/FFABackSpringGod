package com.ffa.back.config;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.*;

@Configuration
public class CacheConfig {

    @Bean
    public ReactiveRedisTemplate<String, JsonNode> reactiveRedisTemplate(ReactiveRedisConnectionFactory factory) {
        Jackson2JsonRedisSerializer<JsonNode> serializer = new Jackson2JsonRedisSerializer<>(JsonNode.class);

        RedisSerializationContext.RedisSerializationContextBuilder<String, JsonNode> builder =
                RedisSerializationContext.newSerializationContext(RedisSerializer.string());

        RedisSerializationContext<String, JsonNode> context = builder
                .value(serializer)
                .build();

        return new ReactiveRedisTemplate<>(factory, context);
    }
}
