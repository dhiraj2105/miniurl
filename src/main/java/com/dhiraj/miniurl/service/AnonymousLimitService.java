package com.dhiraj.miniurl.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class AnonymousLimitService {
    private static final Logger logger = LoggerFactory.getLogger(AnonymousLimitService.class);

    private static final String PREFIX = "anon:";
    private static final Duration TTL = Duration.ofHours(24);

    private final RedisTemplate<String,String> redisTemplate;

    public AnonymousLimitService(RedisTemplate<String,String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public boolean hasAlreadyCreated(String anonKey){
        return redisTemplate.hasKey(PREFIX+anonKey);
    }

    public void markAsCreated(String anonKey,String shortCode){
        redisTemplate.opsForValue().set(PREFIX+anonKey,shortCode, TTL);
    }
}