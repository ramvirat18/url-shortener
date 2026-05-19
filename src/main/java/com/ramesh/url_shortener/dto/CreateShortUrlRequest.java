package com.ramesh.url_shortener.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class CreateShortUrlRequest {

    @NotBlank(message = "original url is required")
    @Pattern(
            regexp = "^(http|https)://.*$",
            message = "Invalid URL format"
    )
    private String originalUrl;
}
