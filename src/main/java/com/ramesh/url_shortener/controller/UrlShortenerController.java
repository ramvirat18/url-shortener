package com.ramesh.url_shortener.controller;

import com.ramesh.url_shortener.dto.CreateShortUrlRequest;
import com.ramesh.url_shortener.entity.ShortUrl;
import com.ramesh.url_shortener.repository.ShortUrlRepository;
import com.ramesh.url_shortener.service.UrlShortenerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/urls")
@RequiredArgsConstructor
public class UrlShortenerController {

    private final UrlShortenerService service;

    @PostMapping
    public ShortUrl createShortUrl(@Valid @RequestBody CreateShortUrlRequest request)
    {
        return service.createShortUrl(request);
    }
}
