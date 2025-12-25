package com.dhiraj.miniurl.dto;

import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class UrlResponse {
    private Long id;
    private String shortCode;
    private String originalUrl;
    private Long clickCount;
    private LocalDateTime createdAt;

    public UrlResponse(
            Long id,
            String shortCode,
            String originalUrl,
            Long clickCount,
            LocalDateTime createdAt
    ) {
        this.id = id;
        this.shortCode = shortCode;
        this.originalUrl = originalUrl;
        this.clickCount = clickCount;
        this.createdAt = createdAt;
    }
}
