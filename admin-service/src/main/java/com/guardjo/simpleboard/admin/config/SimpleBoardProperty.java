package com.guardjo.simpleboard.admin.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("simple-board")
public record SimpleBoardProperty(
        String baseUrl
) {
}
