package com.dhiraj.miniurl.controller;

import com.dhiraj.miniurl.dto.UrlResponse;
import com.dhiraj.miniurl.model.UrlEntity;
import com.dhiraj.miniurl.model.UserEntity;
import com.dhiraj.miniurl.repository.UrlRepository;
import com.dhiraj.miniurl.repository.UserRepository;
import com.dhiraj.miniurl.service.UrlCacheService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserUrlController {

    private final UrlRepository urlRepository;
    private final UserRepository userRepository;
    private final UrlCacheService urlCacheService;

    public UserUrlController(
            UrlRepository urlRepository,
            UserRepository userRepository,
            UrlCacheService urlCacheService) {

        this.urlRepository = urlRepository;
        this.userRepository = userRepository;
        this.urlCacheService = urlCacheService;
    }

    @GetMapping("/urls")
    public ResponseEntity<?> getMyUrls(Authentication authentication) {

        UserEntity user = userRepository
                .findByEmail(authentication.getName())
                .orElseThrow();

        return ResponseEntity.ok(
                urlRepository.findAllByUser_Id(user.getId())
                        .stream()
                        .map(url -> new UrlResponse(
                                url.getId(),
                                url.getShortCode(),
                                url.getOriginalUrl(),
                                url.getClickCount(),
                                url.getCreatedAt()
                        ))
                        .toList()
        );
    }

    @DeleteMapping("/urls/{id}")
    public ResponseEntity<Void> deleteUrl(@PathVariable Long id, Authentication authentication) {
        UserEntity user = userRepository
                .findByEmail(authentication.getName())
                .orElseThrow();
        UrlEntity url = urlRepository
                .findByIdAndUser_Id(id,user.getId())
                .orElseThrow(()->
                        new RuntimeException("Url Not Found or Not owned by the user"));

        urlCacheService.evictUrl(url.getShortCode());
        urlRepository.delete(url);

        return ResponseEntity.noContent().build();
    }
}
