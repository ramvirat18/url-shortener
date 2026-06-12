package com.ramesh.url_shortener.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "short_urls")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShortUrl {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false,columnDefinition = "Text")
    private String originalUrl;
    @Column(nullable = false,unique = true,length = 20)
    private String shortCode;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;
    private Long clickCount;


    @PrePersist
    public void prePersist()
    {
        createdAt=LocalDateTime.now();
        if(clickCount==null)
        {
            clickCount=0L;
        }
    }

}
