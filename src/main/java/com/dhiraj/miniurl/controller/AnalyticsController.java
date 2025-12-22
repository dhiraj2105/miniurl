package com.dhiraj.miniurl.controller;

import com.dhiraj.miniurl.dto.UrlAnalyticsResponse;
import com.dhiraj.miniurl.exception.ShortUrlNotFoundException;
import com.dhiraj.miniurl.model.UrlEntity;
import com.dhiraj.miniurl.repository.UrlRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/analytics")
public class AnalyticsController {

    private final UrlRepository urlRepository;

    public AnalyticsController(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    @GetMapping("/{shortCode}")
    public UrlAnalyticsResponse getAnalytics(@PathVariable String shortCode) {

        UrlEntity entity = urlRepository.findByShortCode(shortCode)
                .orElseThrow(() -> new ShortUrlNotFoundException(shortCode));

        return new UrlAnalyticsResponse(
                entity.getShortCode(),
                entity.getOriginalUrl(),
                entity.getClickCount(),
                entity.getCreatedAt()
        );
    }
}