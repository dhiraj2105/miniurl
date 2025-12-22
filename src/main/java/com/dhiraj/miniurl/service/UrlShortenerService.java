package com.dhiraj.miniurl.service;

import com.dhiraj.miniurl.exception.AnonymousLimitReachedException;
import com.dhiraj.miniurl.exception.ShortUrlNotFoundException;
import com.dhiraj.miniurl.util.ShortCodeGenerator;
import org.springframework.stereotype.Service;

@Service
public class UrlShortenerService {

    private final InMemoryUrlStore urlStore;
    private final AnonymousLimitService anonymousLimitService;

    public UrlShortenerService(
            InMemoryUrlStore urlStore,
            AnonymousLimitService anonymousLimitService) {
        this.urlStore = urlStore;
        this.anonymousLimitService = anonymousLimitService;
    }

    public String shortenUrl(String originalUrl, String anonKey) {

        if (anonymousLimitService.hasAlreadyCreated(anonKey)) {
            throw new AnonymousLimitReachedException();
        }

        String shortCode;
        do {
            shortCode = ShortCodeGenerator.generate();
        } while (urlStore.exists(shortCode));

        urlStore.save(shortCode, originalUrl);
        anonymousLimitService.markAsCreated(anonKey, shortCode);

        return shortCode;
    }

    public String getOriginalUrl(String shortCode) {
        String originalUrl = urlStore.getOriginalUrl(shortCode);

        if (originalUrl == null) {
            throw new ShortUrlNotFoundException(shortCode);
        }

        return originalUrl;
    }
}