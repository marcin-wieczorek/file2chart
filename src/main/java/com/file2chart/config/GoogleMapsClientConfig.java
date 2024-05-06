package com.file2chart.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "application.google", ignoreUnknownFields = false)
@Data
public class GoogleMapsClientConfig {
    private String apiKey;
}
