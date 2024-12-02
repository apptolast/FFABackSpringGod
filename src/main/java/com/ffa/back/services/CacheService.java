package com.ffa.back.services;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class CacheService {
    private final RedisTemplate<String, JsonNode> redisTemplate;

    public CacheService(RedisTemplate<String, JsonNode> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void cacheData(String key, JsonNode data) {
        redisTemplate.opsForValue().set(key, data);
    }

    public JsonNode getCachedData(String key) {
        return redisTemplate.opsForValue().get(key);
    }
}
