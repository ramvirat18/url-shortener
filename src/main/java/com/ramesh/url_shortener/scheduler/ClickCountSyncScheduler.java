package com.ramesh.url_shortener.scheduler;

import com.ramesh.url_shortener.repository.ShortUrlRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class ClickCountSyncScheduler {

    private final RedisTemplate<String, Object> redisTemplate;
    private final ShortUrlRepository repository;

    @Scheduled(fixedRate = 300000)
    public void syncClickCounts() {

        Set<String> keys = redisTemplate.keys("clicks:*");

        if (keys == null || keys.isEmpty()) {
            return;
        }

        for (String key : keys) {

            String shortCode = key.replace("clicks:", "");

            Object value = redisTemplate.opsForValue().get(key);

            if (value == null) {
                continue;
            }

            Long clickCount = ((Number) value).longValue();

            repository.updateClickCount(
                    shortCode,
                    clickCount
            );
        }

        System.out.println("Click counts synced successfully.");
    }
}