package com.ramesh.url_shortener.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateShortUrlResponse {

    private String originalUrl;
    private String shortCode;
    private String shortUrl;
}
