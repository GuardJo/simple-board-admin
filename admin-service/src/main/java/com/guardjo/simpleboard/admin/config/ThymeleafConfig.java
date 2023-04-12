package com.guardjo.simpleboard.admin.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;

@Configuration
public class ThymeleafConfig {
    @Bean
    public SpringResourceTemplateResolver springResourceTemplateResolver(
            SpringResourceTemplateResolver springResourceTemplateResolver, Thymeleaf3Properties properties) {
        springResourceTemplateResolver.setUseDecoupledLogic(properties.decoupledLogic());

        return springResourceTemplateResolver;
    }

    @ConfigurationProperties("spring.thymeleaf3")
    public static record Thymeleaf3Properties(boolean decoupledLogic) {
    }
}
