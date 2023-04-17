package com.guardjo.simpleboard.admin.controller;

import com.guardjo.simpleboard.admin.config.SecurityConfig;
import com.guardjo.simpleboard.admin.controller.constant.UrlConstant;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Import(SecurityConfig.class)
@WebMvcTest(RootController.class)
class RootControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @DisplayName("루트 접근 시 지정된 뷰로 포워딩 테스트")
    @Test
    void testGetForwardingRoot() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("forward:management/article"))
                .andExpect(forwardedUrl(UrlConstant.ARTICLE_MANAGEMENT_URL_PREFIX));
    }
}