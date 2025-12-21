package com.dhiraj.miniurl.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class ShortenUrlRequest {
    @NotBlank(message = "URL must not be empty")
    @Pattern(
            regexp = "^(http|https)://.*$",
            message = "URL must start with http or https"
    )
    private String originalUrl;

    public String getOriginalUrl() {
        return originalUrl;
    }
    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }
}
