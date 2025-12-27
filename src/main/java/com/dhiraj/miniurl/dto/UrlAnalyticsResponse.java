package com.dhiraj.miniurl.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UrlAnalyticsResponse {
    private final long Id;
    private final String shortCode;
    private final String originalUrl;
    private final long clickCount;
    private final LocalDateTime createdAt;

    public UrlAnalyticsResponse(long Id,String shortCode, String originalUrl,
                                long clickCount, LocalDateTime createdAt) {
        this.Id = Id;
        this.shortCode = shortCode;
        this.originalUrl = originalUrl;
        this.clickCount = clickCount;
        this.createdAt = createdAt;
    }

}
