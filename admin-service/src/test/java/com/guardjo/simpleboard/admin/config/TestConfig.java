package com.guardjo.simpleboard.admin.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

@TestConfiguration
@EnableJpaAuditing
public class TestConfig {
    @Bean
    public AuditorAware<String> auditorAware() {
        return () -> Optional.ofNullable("tester");
    }
}
