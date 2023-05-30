package com.guardjo.simpleboard.admin.config;

import com.guardjo.simpleboard.admin.domain.constant.RoleType;
import com.guardjo.simpleboard.admin.model.AdminAccountDto;
import com.guardjo.simpleboard.admin.model.security.BoardAdminPrincipal;
import com.guardjo.simpleboard.admin.model.security.oauth2.kakao.KakaoOAuth2UserResponse;
import com.guardjo.simpleboard.admin.service.AdminAccountService;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        String[] managerRole = {RoleType.MANAGER.name(), RoleType.DEVELOPER.name(), RoleType.ADMIN.name()};

        httpSecurity.authorizeRequests((auth) ->
                        auth.requestMatchers(
                                        PathRequest.toStaticResources().atCommonLocations()
                                )
                                .permitAll()
                                .antMatchers(
                                        HttpMethod.GET,
                                        "/static/plugins/**"
                                )
                                .permitAll()
                                .mvcMatchers(HttpMethod.POST, "/**")
                                .hasAnyRole(managerRole)
                                .mvcMatchers(HttpMethod.DELETE, "/**")
                                .hasAnyRole(managerRole)
                                .anyRequest().authenticated()
                ).formLogin(withDefaults())
                .oauth2Login(withDefaults())
                .logout(logOutConfig -> logOutConfig.logoutSuccessUrl("/"));

        return httpSecurity.build();
    }

    @Bean
    public UserDetailsService userDetailsService(AdminAccountService adminAccountService) {
        return username ->
                adminAccountService.searchAdminAccount(username)
                        .map(BoardAdminPrincipal::from)
                        .orElseThrow(() -> new UsernameNotFoundException(String.format("Not Found AdminAccount, email = %s", username)));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService(AdminAccountService adminAccountService, PasswordEncoder passwordEncoder) {
        DefaultOAuth2UserService defaultOAuth2UserService = new DefaultOAuth2UserService();

        return userRequest -> {
            OAuth2User oAuth2User = defaultOAuth2UserService.loadUser(userRequest);
            KakaoOAuth2UserResponse kakaoOAuth2UserResponse = KakaoOAuth2UserResponse.from(oAuth2User.getAttributes());

            AdminAccountDto adminAccountDto = adminAccountService.searchAdminAccount(kakaoOAuth2UserResponse.kakaoAccount().email())
                    .orElseGet(() -> adminAccountService.createAdminAccount(
                            kakaoOAuth2UserResponse.kakaoAccount().email(),
                            kakaoOAuth2UserResponse.kakaoAccount().name(),
                            passwordEncoder.encode(randomPassword(kakaoOAuth2UserResponse.id())))
                    );

            return BoardAdminPrincipal.from(adminAccountDto);
        };
    }

    private String randomPassword(long kakaoRequestId) {
        return "{bcryp}kakao_random_password" + String.valueOf(kakaoRequestId);
    }
}
