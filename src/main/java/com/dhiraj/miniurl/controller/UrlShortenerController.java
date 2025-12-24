package com.dhiraj.miniurl.controller;

import com.dhiraj.miniurl.dto.ShortenUrlRequest;
import com.dhiraj.miniurl.dto.ShortenUrlResponse;
import com.dhiraj.miniurl.exception.ShortUrlNotFoundException;
import com.dhiraj.miniurl.service.UrlShortenerService;
import com.dhiraj.miniurl.util.AnonymousUserUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class UrlShortenerController {

    @Value("${app.base-url}")
    private String baseUrl;

    private final UrlShortenerService urlShortenerService;

    public UrlShortenerController(UrlShortenerService urlShortenerService) {
        this.urlShortenerService = urlShortenerService;
    }

    @PostMapping("/shorten")
    public ResponseEntity<ShortenUrlResponse> shortenUrl(
            @Valid @RequestBody ShortenUrlRequest request,
            HttpServletRequest httpRequest) {

        String anonKey = AnonymousUserUtil.generateAnonKey(httpRequest);

        String shortCode =
                urlShortenerService.shortenUrl(request.getOriginalUrl(), anonKey);

//        String shortUrl = "http://localhost:8080/" + shortCode;
            String shortUrl = baseUrl +"/"+ shortCode;

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ShortenUrlResponse(shortUrl));
    }


    @GetMapping("/expand")
    public ResponseEntity<Map<String, String>> expandUrl(@RequestParam String code) {
        try {
            String originalUrl = urlShortenerService.getOriginalUrl(code);
            return ResponseEntity.ok(Map.of("originalUrl", originalUrl));
        } catch (ShortUrlNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Short URL not found"));
        }
    }

}