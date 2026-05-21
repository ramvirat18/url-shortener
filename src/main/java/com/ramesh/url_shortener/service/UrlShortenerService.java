package com.ramesh.url_shortener.service;

import com.ramesh.url_shortener.dto.CreateShortUrlRequest;
import com.ramesh.url_shortener.entity.ShortUrl;
import com.ramesh.url_shortener.repository.ShortUrlRepository;
import com.ramesh.url_shortener.util.ShortCodeGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UrlShortenerService {

    private  final ShortUrlRepository repository;
    String shortCode;

    public ShortUrl createShortUrl(CreateShortUrlRequest request)
    {
        do{
            shortCode= ShortCodeGenerator.generate();
        } while(repository.existsByShortCode(shortCode));

            ShortUrl shortUrl = ShortUrl.builder()
                    .originalUrl(request.getOriginalUrl())
                    .shortCode(shortCode)
                    .build();

            return repository.save(shortUrl);


    }

    public String getOriginalUrl(String shortCode) {
        ShortUrl shortUrl = repository.findByShortCode(shortCode).
                orElseThrow(()->new RuntimeException("short code not found"));
        shortUrl.setClickCount(shortUrl.getClickCount()+1);
        repository.save(shortUrl);
        return shortUrl.getOriginalUrl();
    }
}
