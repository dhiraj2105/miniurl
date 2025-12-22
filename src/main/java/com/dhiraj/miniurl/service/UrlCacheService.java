package com.dhiraj.miniurl.service;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class UrlCacheService {
    private static final Duration CACHE_TTL = Duration.ofHours(24);

    private final StringRedisTemplate redisTemplate;

    public UrlCacheService(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public String getCachedUrl(String shortCode) {
        return redisTemplate.opsForValue()
                .get(buildUrlKey(shortCode));
    }

    public void cacheUrl(String shortCode, String originalUrl) {
        redisTemplate.opsForValue()
                .set(buildUrlKey(shortCode), originalUrl, CACHE_TTL);
    }

    public void incrementClickCount(String shortCode) {
        redisTemplate.opsForValue()
                .increment(buildClickKey(shortCode));
    }

    public Long getClickCount(String shortCode) {
        String value = redisTemplate.opsForValue()
                .get(buildClickKey(shortCode));
        return value == null ? 0L : Long.parseLong(value);
    }

    private String buildUrlKey(String shortCode) {
        return "url:" + shortCode;
    }

    private String buildClickKey(String shortCode) {
        return "click:" + shortCode;
    }
}