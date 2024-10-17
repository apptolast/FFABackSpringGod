package com.ffa.back.services;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class CacheService {

    private final ReactiveRedisTemplate<String, JsonNode> reactiveRedisTemplate;

    public CacheService(ReactiveRedisTemplate<String, JsonNode> reactiveRedisTemplate) {
        this.reactiveRedisTemplate = reactiveRedisTemplate;
    }

    public Mono<Boolean> cacheData(String key, JsonNode data) {
        return reactiveRedisTemplate.opsForValue().set(key, data);
    }

    public Mono<JsonNode> getCachedData(String key) {
        return reactiveRedisTemplate.opsForValue().get(key);
    }
}
