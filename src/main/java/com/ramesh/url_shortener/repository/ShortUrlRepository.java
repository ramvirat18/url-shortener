package com.ramesh.url_shortener.repository;

import com.ramesh.url_shortener.entity.ShortUrl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface ShortUrlRepository extends JpaRepository<ShortUrl, Long> {

    Optional<ShortUrl> findByShortCode(String shortCode);

    boolean existsByShortCode(String shortCode);

    @Modifying
    @Transactional
    @Query("""
           UPDATE ShortUrl s
           SET s.clickCount = :clickCount
           WHERE s.shortCode = :shortCode
           """)
    void updateClickCount(
            @Param("shortCode") String shortCode,
            @Param("clickCount") Long clickCount
    );
}