package com.dhiraj.miniurl.service;

import com.dhiraj.miniurl.dto.UrlResponse;
import com.dhiraj.miniurl.model.UrlEntity;
import com.dhiraj.miniurl.model.UserEntity;
import com.dhiraj.miniurl.repository.UrlRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UrlService {
    private final UrlRepository urlRepository;

    public UrlService(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    public List<UrlResponse> getUserUrls(UserEntity user){
        List<UrlEntity> urls = urlRepository.findAllByUser_Id(user.getId());

        return urls.stream()
                .map(url->new UrlResponse(
                        url.getId(),
                        url.getShortCode(),
                        url.getOriginalUrl(),
                        url.getClickCount(),
                        url.getCreatedAt()
                ))
                .collect(Collectors.toList());
    }
}
