package com.guardjo.simpleboard.admin.config;

import com.guardjo.simpleboard.admin.service.AdminAccountService;
import com.guardjo.simpleboard.admin.util.TestDateGenerator;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.event.annotation.BeforeTestMethod;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@Import(SecurityConfig.class)
@TestConfiguration
public class TestSecurityConfig {
    @MockBean
    private AdminAccountService adminAccountService;

    @BeforeTestMethod
    public void init() {
        given(adminAccountService.searchAdminAccount(anyString()))
                .willReturn(Optional.of(TestDateGenerator.generateAccountDto()));
    }
}
