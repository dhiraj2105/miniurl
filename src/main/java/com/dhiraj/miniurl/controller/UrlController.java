package com.dhiraj.miniurl.controller;

import com.dhiraj.miniurl.dto.UrlResponse;
import com.dhiraj.miniurl.model.UserEntity;
import com.dhiraj.miniurl.service.UrlService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/urls")
public class UrlController {
    private final UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    @GetMapping
    public List<UrlResponse> getUserUrls( @AuthenticationPrincipal UserEntity user){
        return urlService.getUserUrls(user);
    }

}
