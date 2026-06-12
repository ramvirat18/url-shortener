package com.ramesh.url_shortener.scheduler;

import com.ramesh.url_shortener.repository.ShortUrlRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ClickCountSyncScheduler {

    private final ShortUrlRepository repository;

    @Scheduled(fixedRate = 300000)
    public void syncClickCounts()
    {
        System.out.println( "Sync job executed...");
    }

}
