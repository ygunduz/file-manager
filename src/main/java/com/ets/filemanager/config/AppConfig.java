package com.ets.filemanager.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "app.demo")
public class AppConfig {

    private String jwtSecret;
    private long jwtExpirationMs;
}
