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
@WebMvcTest(CommentManagementController.class)
class CommentManagementControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @DisplayName("댓글 관리 뷰 페이지 반환 테스트")
    @Test
    void testGetCommentManagementView() throws Exception {
        mockMvc.perform(get(UrlConstant.COMMENT_MANAGEMENT_URL_PREFIX))
                .andExpect(status().isOk())
                .andExpect(view().name("management/comment"))
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML));
    }
}