package com.dhiraj.miniurl.service;

import com.dhiraj.miniurl.repository.UrlRepository;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ClickSyncService {

    private final StringRedisTemplate redisTemplate;
    private final UrlRepository urlRepository;

    public ClickSyncService(
            StringRedisTemplate redisTemplate,
            UrlRepository urlRepository) {
        this.redisTemplate = redisTemplate;
        this.urlRepository = urlRepository;
    }

    /**
     * Runs every 5 minutes
     */
    @Scheduled(fixedRate = 300_000)
    public void syncClickCounts() {

        Set<String> keys = redisTemplate.keys("click:*");
        if (keys == null || keys.isEmpty()) {
            return;
        }

        for (String key : keys) {
            String shortCode = key.replace("click:", "");
            String value = redisTemplate.opsForValue().get(key);

            if (value == null) continue;

            long clicks = Long.parseLong(value);

            urlRepository.findByShortCode(shortCode)
                    .ifPresent(entity -> {
                        entity.incrementClicks(clicks);
                        urlRepository.save(entity);
                        redisTemplate.delete(key);
                    });
        }
    }
}