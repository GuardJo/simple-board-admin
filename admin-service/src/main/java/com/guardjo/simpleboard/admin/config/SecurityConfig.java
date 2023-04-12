package com.guardjo.simpleboard.admin.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests((auth) ->
                        auth.requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                                .permitAll()
                                .mvcMatchers(
                                        HttpMethod.GET,
                                        "/",
                                        "/article",
                                        "/article/search-hashtag"
                                ).permitAll()
                                .anyRequest().permitAll()
                ).formLogin(withDefaults())
                .logout(logOutConfig -> logOutConfig.logoutSuccessUrl("/"));

        return httpSecurity.build();
    }
}
