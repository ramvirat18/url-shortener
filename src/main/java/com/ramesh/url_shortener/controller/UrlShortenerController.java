package com.ramesh.url_shortener.controller;

import com.ramesh.url_shortener.dto.CreateShortUrlRequest;
import com.ramesh.url_shortener.dto.CreateShortUrlResponse;
import com.ramesh.url_shortener.dto.UrlAnalyticsResponse;
import com.ramesh.url_shortener.entity.ShortUrl;
import com.ramesh.url_shortener.exception.RateLimitExceededException;
import com.ramesh.url_shortener.repository.ShortUrlRepository;
import com.ramesh.url_shortener.service.RateLimitService;
import com.ramesh.url_shortener.service.UrlShortenerService;
import jakarta.servlet.http.HttpServletRequest;
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
    private final RateLimitService rateLimitService;

    @PostMapping
    public CreateShortUrlResponse createShortUrl(@Valid @RequestBody CreateShortUrlRequest request)
    {
        return service.createShortUrl(request);
    }

    @GetMapping("/{shortCode}")
    public ResponseEntity<Void> redirectUrl(@PathVariable String shortCode, HttpServletRequest request)
    {

        String ipAddress = request.getRemoteAddr();
        boolean allowed = rateLimitService.isAllowed(ipAddress);

        if(!allowed)
        {
            throw new RateLimitExceededException("Rate limit exceed");
        }
        String originalUrl = service.getOriginalUrl(shortCode);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(originalUrl));
        return new ResponseEntity<>(headers, HttpStatus.FOUND);


    }

    @GetMapping("/{shortCode}/analytics")
    public UrlAnalyticsResponse getAnalytics(@PathVariable String shortCode)
    {
        return service.getAnalytics(shortCode);
    }

}
