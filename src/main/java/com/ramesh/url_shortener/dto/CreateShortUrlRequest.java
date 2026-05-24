package com.ramesh.url_shortener.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateShortUrlRequest {

    @NotBlank(message = "original url is required")
    @Pattern(
            regexp = "^(https?://).+$",
            message = "URL must start with http:// or https://"
    )
    @Size(max = 2048, message = "URL too long")
    private String originalUrl;
}
