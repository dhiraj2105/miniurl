package com.dhiraj.miniurl.service;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class InMemoryUrlStore {

    private final Map<String, String> urlMap = new ConcurrentHashMap<>();

    public void save(String shortCode, String originalUrl) {
        urlMap.put(shortCode, originalUrl);
    }

    public String getOriginalUrl(String shortCode) {
        return urlMap.get(shortCode);
    }

    public boolean exists(String shortCode) {
        return urlMap.containsKey(shortCode);
    }
}
