package com.guardjo.simpleboard.admin.controller;

import com.guardjo.simpleboard.admin.config.SecurityConfig;
import com.guardjo.simpleboard.admin.config.TestSecurityConfig;
import com.guardjo.simpleboard.admin.controller.constant.UrlConstant;
import com.guardjo.simpleboard.admin.model.ArticleDto;
import com.guardjo.simpleboard.admin.service.ArticleManagementService;
import com.guardjo.simpleboard.admin.util.TestDateGenerator;
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
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Import(TestSecurityConfig.class)
@WebMvcTest(ArticleManagementController.class)
class ArticleManagementControllerTest {
    @Mock
    private ArticleManagementService articleManagementService;
    @Autowired
    private MockMvc mockMvc;

    @DisplayName("게시글 관리자 뷰 반환 테스트")
    @Test
    @WithMockUser(username = "test@mail.com")
    void testGetArticleManagementView() throws Exception {
        List<ArticleDto> expected = List.of(TestDateGenerator.generateArticleDto("test", "test"));

        given(articleManagementService.findArticles()).willReturn(expected);

        mockMvc.perform(get(UrlConstant.ARTICLE_MANAGEMENT_URL_PREFIX))
                .andExpect(status().isOk())
                .andExpect(view().name("management/article"))
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(model().attribute("articles", expected));

        then(articleManagementService).should().findArticles();
    }

    @DisplayName("게시글 관리자 페이지에서 게시글 단일 정보 반환 테스트")
    @Test
    @WithMockUser(username = "test@mail.com")
    void testGetArticleData() throws Exception {
        ArticleDto articleDto = TestDateGenerator.generateArticleDto("test", "test");
        long articleId = 1L;

        given(articleManagementService.findArticle(articleId)).willReturn(articleDto);

        mockMvc.perform(get(UrlConstant.ARTICLE_MANAGEMENT_URL_PREFIX + "/" + articleDto))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title").value(articleDto.title()))
                .andExpect(jsonPath("$.content").value(articleDto.content()));

        then(articleManagementService).should().findArticle(articleId);
    }

    @DisplayName("게시글 관리자 페이지에서 게시글 단일 정보 삭제 테스트")
    @Test
    @WithMockUser(username = "test@mail.com")
    void testDeleteArticle() throws Exception {
        long articleId = 1L;

        willDoNothing().given(articleManagementService).deleteArticle(articleId);

        mockMvc.perform(delete(UrlConstant.ARTICLE_MANAGEMENT_URL_PREFIX + "/" + articleId))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/management/article"))
                .andExpect(redirectedUrl(UrlConstant.ARTICLE_MANAGEMENT_URL_PREFIX));

        then(articleManagementService).should().deleteArticle(articleId);
    }
}