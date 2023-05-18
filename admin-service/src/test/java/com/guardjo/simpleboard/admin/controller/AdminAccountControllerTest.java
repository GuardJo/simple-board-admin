package com.guardjo.simpleboard.admin.controller;

import com.guardjo.simpleboard.admin.config.SecurityConfig;
import com.guardjo.simpleboard.admin.config.TestSecurityConfig;
import com.guardjo.simpleboard.admin.controller.constant.UrlConstant;
import com.guardjo.simpleboard.admin.model.AdminAccountDto;
import com.guardjo.simpleboard.admin.service.AdminAccountService;
import com.guardjo.simpleboard.admin.util.TestDateGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.event.annotation.BeforeTestMethod;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Import(SecurityConfig.class)
@WebMvcTest(AdminAccountController.class)
class AdminAccountControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AdminAccountService adminAccountService;

    @BeforeTestMethod
    public void init() {
        given(adminAccountService.searchAdminAccount(anyString()))
                .willReturn(Optional.of(TestDateGenerator.generateAccountDto()));
    }

    @DisplayName("어드민 회원 관리 뷰페이지 반환 테스트")
    @Test
    @WithMockUser(username = "test@mail.com")
    void testGetAdminAccountView() throws Exception {
        given(adminAccountService.findAdminAccounts()).willReturn(List.of());

        mockMvc.perform(get(UrlConstant.ADMIN_ACCOUNT_URL_PREFIX))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/account"))
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML));

        then(adminAccountService).should().findAdminAccounts();
    }

    @DisplayName("어드민 회원 목록 데이터 반환 테스트")
    @Test
    @WithMockUser(username = "test@mail.com")
    void testGetAdminAccountData() throws Exception {
        given(adminAccountService.findAdminAccounts()).willReturn(List.of());

        mockMvc.perform(get(UrlConstant.ADMIN_ACCOUNT_API_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

        then(adminAccountService).should().findAdminAccounts();
    }

    @DisplayName("특정 어드민 회원 삭제 테스트")
    @Test
    @WithMockUser(username = "test@mail.com", roles = {"ADMIN"})
    void testDeleteAdminAccountData() throws Exception {
        String email = "test@mail.com";

        willDoNothing().given(adminAccountService).deleteAdminAccount(email);

        mockMvc.perform(delete(UrlConstant.ADMIN_ACCOUNT_API_URL + "/" + email))
                .andExpect(status().isNoContent());

        then(adminAccountService).should().deleteAdminAccount(email);
    }
}