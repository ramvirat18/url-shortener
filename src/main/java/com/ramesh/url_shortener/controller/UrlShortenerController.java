package com.ramesh.url_shortener.controller;

import com.ramesh.url_shortener.dto.CreateShortUrlRequest;
import com.ramesh.url_shortener.entity.ShortUrl;
import com.ramesh.url_shortener.repository.ShortUrlRepository;
import com.ramesh.url_shortener.service.UrlShortenerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

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

    @GetMapping("/{shortCode}")
    public ResponseEntity<Void> redirectUrl(@PathVariable String shortCode)
    {
        String originalUrl = service.getOriginalUrl(shortCode);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(originalUrl));
        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }

}
