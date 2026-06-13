package com.ramesh.url_shortener.service;

import com.ramesh.url_shortener.dto.CreateShortUrlRequest;
import com.ramesh.url_shortener.dto.CreateShortUrlResponse;
import com.ramesh.url_shortener.dto.UrlAnalyticsResponse;
import com.ramesh.url_shortener.entity.ShortUrl;
import com.ramesh.url_shortener.exception.ResourceNotFoundException;
import com.ramesh.url_shortener.repository.ShortUrlRepository;
import com.ramesh.url_shortener.util.ShortCodeGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class UrlShortenerService {

    private  final ShortUrlRepository repository;
    String shortCode;
    private final RedisTemplate<String,Object> redisTemplate;

    public CreateShortUrlResponse createShortUrl(CreateShortUrlRequest request)
    {
        do{
            shortCode= ShortCodeGenerator.generate();
        } while(repository.existsByShortCode(shortCode));

            ShortUrl shortUrl = ShortUrl.builder()
                    .originalUrl(request.getOriginalUrl())
                    .shortCode(shortCode)
                    .build();

             repository.save(shortUrl);

             redisTemplate.opsForValue().set(
                     shortCode,shortUrl.getOriginalUrl(), Duration.ofHours(24)
             );

             return CreateShortUrlResponse.builder()
                     .originalUrl(shortUrl.getOriginalUrl())
                     .shortCode(shortUrl.getShortCode())

                     .shortUrl("http://localhost:8080/api/v1/urls"+shortCode)
                     .build();


    }

    public String getOriginalUrl(String shortCode) {


        String cachedUrl= (String) redisTemplate.opsForValue().get(shortCode);
        if(cachedUrl!=null)
        {
            //incrementCount(shortCode);
            redisTemplate.opsForValue().increment("clicks:"+shortCode);
            return cachedUrl;
        }

        ShortUrl shortUrl = findUrlOrThrow(shortCode);

        redisTemplate.opsForValue().set(shortCode,shortUrl.getOriginalUrl(),Duration.ofHours(24));
        //shortUrl.setClickCount(shortUrl.getClickCount()+1);
        redisTemplate.opsForValue().increment("clicks:"+shortCode);
        repository.save(shortUrl);
        return shortUrl.getOriginalUrl();
    }



    public UrlAnalyticsResponse getAnalytics(String shortCode)
    {
        ShortUrl shortUrl = findUrlOrThrow(shortCode);

        return UrlAnalyticsResponse.builder()
                .originalUrl(shortUrl.getOriginalUrl())
                .shortCode(shortUrl.getShortCode())
                .clickCount(shortUrl.getClickCount())
                .createdAt(shortUrl.getCreatedAt())
                .build();
    }

    public ShortUrl findUrlOrThrow(String shortCode) {
        return repository.findByShortCode(shortCode).
                orElseThrow(() -> new ResourceNotFoundException("ShortUrl is not found"));
    }
}
