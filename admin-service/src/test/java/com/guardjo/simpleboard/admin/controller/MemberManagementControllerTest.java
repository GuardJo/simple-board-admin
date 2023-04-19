package com.guardjo.simpleboard.admin.controller;

import com.guardjo.simpleboard.admin.config.SecurityConfig;
import com.guardjo.simpleboard.admin.controller.constant.UrlConstant;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Import(SecurityConfig.class)
@WebMvcTest(MemberManagementController.class)
class MemberManagementControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @DisplayName("게시판 회원 관리 뷰페이지 반환 테스트")
    @Test
    void testGetMemberManagementView() throws Exception {
        mockMvc.perform(get(UrlConstant.MEMBER_MANAGEMENT_URL_PREFIX))
                .andExpect(status().isOk())
                .andExpect(view().name("management/member"))
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML));
    }
}