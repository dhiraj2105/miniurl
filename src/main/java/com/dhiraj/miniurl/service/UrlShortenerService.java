package com.dhiraj.miniurl.service;

import com.dhiraj.miniurl.exception.AnonymousLimitReachedException;
import com.dhiraj.miniurl.exception.ShortUrlNotFoundException;
import com.dhiraj.miniurl.model.UrlEntity;
import com.dhiraj.miniurl.repository.UrlRepository;
import com.dhiraj.miniurl.util.ShortCodeGenerator;
import org.springframework.stereotype.Service;

@Service
public class UrlShortenerService {

    private final UrlRepository urlRepository;
    private final AnonymousLimitService anonymousLimitService;

    public UrlShortenerService(
            UrlRepository urlRepository,
            AnonymousLimitService anonymousLimitService) {
        this.urlRepository = urlRepository;
        this.anonymousLimitService = anonymousLimitService;
    }

    public String shortenUrl(String originalUrl, String anonKey) {

        if (anonymousLimitService.hasAlreadyCreated(anonKey)) {
            throw new AnonymousLimitReachedException();
        }

        String shortCode;
        do {
            shortCode = ShortCodeGenerator.generate();
        } while (urlRepository.existsByShortCode(shortCode));

        UrlEntity entity =
                new UrlEntity(shortCode, originalUrl, true);

        urlRepository.save(entity);
        anonymousLimitService.markAsCreated(anonKey, shortCode);

        return shortCode;
    }

    public String getOriginalUrl(String shortCode) {

        UrlEntity entity = urlRepository.findByShortCode(shortCode)
                .orElseThrow(() -> new ShortUrlNotFoundException(shortCode));

        return entity.getOriginalUrl();
    }
}