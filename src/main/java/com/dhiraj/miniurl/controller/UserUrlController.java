package com.dhiraj.miniurl.controller;

import com.dhiraj.miniurl.dto.UrlResponse;
import com.dhiraj.miniurl.model.UserEntity;
import com.dhiraj.miniurl.repository.UrlRepository;
import com.dhiraj.miniurl.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserUrlController {

    private final UrlRepository urlRepository;
    private final UserRepository userRepository;

    public UserUrlController(
            UrlRepository urlRepository,
            UserRepository userRepository) {

        this.urlRepository = urlRepository;
        this.userRepository = userRepository;
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
}
