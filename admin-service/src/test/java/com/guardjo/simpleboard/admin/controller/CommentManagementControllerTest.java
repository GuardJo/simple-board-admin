package com.guardjo.simpleboard.admin.controller;

import com.guardjo.simpleboard.admin.config.TestSecurityConfig;
import com.guardjo.simpleboard.admin.controller.constant.UrlConstant;
import com.guardjo.simpleboard.admin.service.CommentManagementService;
import com.guardjo.simpleboard.admin.util.TestDataGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Import(TestSecurityConfig.class)
@WebMvcTest(CommentManagementController.class)
class CommentManagementControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Mock
    private CommentManagementService commentManagementService;

    @DisplayName("댓글 관리 뷰 페이지 반환 테스트")
    @Test
    @WithMockUser(username = "test@mail.com")
    void testGetCommentManagementView() throws Exception {
        given(commentManagementService.findComments()).willReturn(List.of());

        mockMvc.perform(get(UrlConstant.COMMENT_MANAGEMENT_URL_PREFIX))
                .andExpect(status().isOk())
                .andExpect(view().name("management/comment"))
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(model().attribute("comments", List.of()));

        then(commentManagementService).should().findComments();
    }

    @DisplayName("댓글 관리 뷰에서 특정 댓글 객체 반환 테스트")
    @Test
    @WithMockUser(username = "test@mail.com")
    void testGetComment() throws Exception {
        long commentId = 1L;
        String content = "test";
        given(commentManagementService.findComment(commentId))
                .willReturn(TestDataGenerator.generateCommentDto(content));

        mockMvc.perform(get(UrlConstant.COMMENT_MANAGEMENT_URL_PREFIX + "/" + commentId))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(commentId))
                .andExpect(jsonPath("$.content").value(content));

        then(commentManagementService).should().findComment(commentId);
    }

    @DisplayName("댓글 관리 뷰에서 특정 댓글 삭제 반환 테스트")
    @Test
    @WithMockUser(username = "test@mail.com")
    void testDeleteComment() throws Exception {
        long commentId = 1L;

        willDoNothing().given(commentManagementService).deleteComment(commentId);

        mockMvc.perform(delete(UrlConstant.COMMENT_MANAGEMENT_URL_PREFIX + "/" + commentId))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(UrlConstant.COMMENT_MANAGEMENT_URL_PREFIX))
                .andExpect(view().name("redirect:/management/comments"));

        then(commentManagementService).should().deleteComment(commentId);
    }
}