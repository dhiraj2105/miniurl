package com.dhiraj.miniurl.dto;

import lombok.Getter;

@Getter
public class ShortenUrlResponse {
    private String shortUrl;

    public ShortenUrlResponse(String shortUrl) {
        this.shortUrl = shortUrl;
    }

}
