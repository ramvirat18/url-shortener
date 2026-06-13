package com.ramesh.url_shortener.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class RateLimitService {

    private final RedisTemplate<String,Object> redisTemplate;
    private  static final long MAX_REQUESTS=100;

    public boolean isAllowed(String ipAddress)
    {
        String key = "rate:" + ipAddress;

        Long count=redisTemplate.opsForValue().increment(key);
        if(count==1)
        {
            redisTemplate.expire(key, Duration.ofMinutes(1));
        }

        return count<=MAX_REQUESTS;
    }

}
