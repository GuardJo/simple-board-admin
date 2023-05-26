package com.guardjo.simpleboard.admin.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.guardjo.simpleboard.admin.config.SimpleBoardProperty;
import com.guardjo.simpleboard.admin.domain.constant.SimpleBoardUrls;
import com.guardjo.simpleboard.admin.model.ArticleDto;
import com.guardjo.simpleboard.admin.model.response.ArticleResponse;
import com.guardjo.simpleboard.admin.util.TestDateGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.client.MockRestServiceServer;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@ActiveProfiles("test")
class ArticleManagementServiceTest {
    @Deprecated
    @DisplayName("실제 서비스 연동 테스트")
    @SpringBootTest
    @Nested
    class RealServiceTest {
        @Autowired
        private ArticleManagementService articleManagementService;

        @DisplayName("게시판 서비스에서 게시글 목록 반환 테스트")
        @Test
        void testFindArticles() {
            List<ArticleDto> result = articleManagementService.findArticles();

            assertThat(result).isNotNull();

            System.out.println(result.stream().findFirst());
        }

        @DisplayName("게시판 서비스에서 특정 게시글 반환 테스트")
        @Test
        void testFindArticle() {
            long articleId = 2L;
            ArticleDto result = articleManagementService.findArticle(articleId);

            assertThat(result).isNotNull();

            System.out.println(result);
        }

        @DisplayName("게시판 서비스에서 특정 게시글 삭제 테스트")
        @Test
        void testDeleteArticle() {
            long articleId = 1L;

            assertThatCode(() -> articleManagementService.deleteArticle(articleId)).doesNotThrowAnyException();
        }
    }

    @DisplayName("외부 서비스 요청 모킹 테스트")
    @EnableConfigurationProperties(SimpleBoardProperty.class)
    @AutoConfigureWebClient(registerRestTemplate = true)
    @RestClientTest(ArticleManagementService.class)
    @Nested
    class RestTemplateTest {
        private final SimpleBoardProperty simpleBoardProperty;
        private final ObjectMapper objectMapper;
        private final MockRestServiceServer restServiceServer;
        private final ArticleManagementService articleManagementService;

        @Autowired
        RestTemplateTest(SimpleBoardProperty simpleBoardProperty,
                         ObjectMapper objectMapper,
                         MockRestServiceServer restServiceServer,
                         ArticleManagementService articleManagementService) {
            this.simpleBoardProperty = simpleBoardProperty;
            this.objectMapper = objectMapper;
            this.restServiceServer = restServiceServer;
            this.articleManagementService = articleManagementService;
        }

        @DisplayName("게시판 서비스에서 게시글 목록 반환 테스트")
        @Test
        void testFindArticles() throws JsonProcessingException {
            List<ArticleDto> expected = List.of(TestDateGenerator.generateArticleDto("test", "test"));
            ArticleResponse articleResponse = TestDateGenerator.generateArticleResponse(expected);

            restServiceServer.expect(
                            requestTo(simpleBoardProperty.baseUrl() + SimpleBoardUrls.REQUEST_ARTICLES_URL + "?size=" + Integer.MAX_VALUE)
                    ).andExpect(method(HttpMethod.GET))
                    .andRespond(withSuccess(
                            objectMapper.writeValueAsString(articleResponse),
                            MediaType.APPLICATION_JSON
                    ));

            List<ArticleDto> actual = articleManagementService.findArticles();

            assertThat(actual.stream().findFirst()).isEqualTo(expected.stream().findFirst());
            restServiceServer.verify();
        }

        @DisplayName("게시판 서비스에서 특정 게시글 반환 테스트")
        @Test
        void testFindArticle() throws JsonProcessingException {
            long articleId = 1L;
            ArticleDto expected = TestDateGenerator.generateArticleDto("test", "test");

            restServiceServer.expect(
                            requestTo(simpleBoardProperty.baseUrl() + SimpleBoardUrls.REQUEST_ARTICLES_URL + "/" + articleId))
                    .andExpect(method(HttpMethod.GET))
                    .andRespond(withSuccess(
                            objectMapper.writeValueAsString(expected),
                            MediaType.APPLICATION_JSON
                    ));

            ArticleDto actual = articleManagementService.findArticle(articleId);

            assertThat(actual).isEqualTo(expected);

            restServiceServer.verify();
        }

        @DisplayName("게시판 서비스에서 특정 게시글 삭제 테스트")
        @Test
        void testDeleteArticle() {
            long articleId = 1L;

            restServiceServer.expect(
                            requestTo(simpleBoardProperty.baseUrl() + SimpleBoardUrls.REQUEST_ARTICLES_URL + "/" + articleId))
                    .andExpect(method(HttpMethod.DELETE))
                    .andRespond(withSuccess());

            assertThatCode(() -> articleManagementService.deleteArticle(articleId)).doesNotThrowAnyException();

            restServiceServer.verify();
        }
    }
}