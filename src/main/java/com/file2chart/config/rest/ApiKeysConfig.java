package com.file2chart.config.rest;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "application.api-keys", ignoreUnknownFields = false)
@Data
public class ApiKeysConfig {
    private String google;
    private String rapidApi;
    private String rapidApiHost;
}
