package com.guardjo.simpleboard.admin.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.guardjo.simpleboard.admin.config.SimpleBoardProperty;
import com.guardjo.simpleboard.admin.domain.constant.SimpleBoardUrls;
import com.guardjo.simpleboard.admin.model.CommentDto;
import com.guardjo.simpleboard.admin.model.response.CommentResponse;
import com.guardjo.simpleboard.admin.util.TestDataGenerator;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@ActiveProfiles("test")
class CommentManagementServiceTest {
    @DisplayName("실제 서비스 연동 테스트")
    @SpringBootTest
    @Nested
    class RealServiceTest {
        private final CommentManagementService commentManagementService;

        @Autowired
        RealServiceTest(CommentManagementService commentManagementService) {
            this.commentManagementService = commentManagementService;
        }

        @DisplayName("게시판 서베스에서 댓글 목록 반환 테스트")
        @Test
        void testFindComments() {
            List<CommentDto> commentDtos = commentManagementService.findComments();

            assertThat(commentDtos).isNotNull();

            commentDtos.forEach(commentDto -> System.out.println(commentDto.toString()));
        }

        @DisplayName("게시판 서비스에서 특정 댓글 반환 테스트")
        @Test
        void testFindComment() {
            long commentId = 1L;

            CommentDto commentDto = commentManagementService.findComment(commentId);

            assertThat(commentDto).isNotNull();
        }

        @DisplayName("게시판 서비스에서 특정 댓글 삭제 테스튼")
        @Test
        void testDeleteComment() {
            long commentId = 1L;

            assertThatCode(() -> commentManagementService.deleteComment(commentId))
                    .doesNotThrowAnyException();
        }
    }
    
    @DisplayName("외부 서비스 요청 모킹 테스트")
    @RestClientTest(CommentManagementServiceTest.class)
    @AutoConfigureWebClient(registerRestTemplate = true)
    @EnableConfigurationProperties(SimpleBoardProperty.class)
    @Nested
    class RestTemplateTest {
        private final SimpleBoardProperty simpleBoardProperty;
        private final ObjectMapper objectMapper;
        private final MockRestServiceServer mockRestServiceServer;
        private final CommentManagementService commentManagementService;

        @Autowired
        RestTemplateTest(SimpleBoardProperty simpleBoardProperty,
                         ObjectMapper objectMapper,
                         MockRestServiceServer mockRestServiceServer,
                         CommentManagementService commentManagementService) {
            this.simpleBoardProperty = simpleBoardProperty;
            this.objectMapper = objectMapper;
            this.mockRestServiceServer = mockRestServiceServer;
            this.commentManagementService = commentManagementService;
        }

        @DisplayName("게시판 서비스에서 댓글 목록 반환 테스트")
        @Test
        void testFindComments() throws JsonProcessingException {
            List<CommentDto> expected = List.of(TestDataGenerator.generateCommentDto("test"));
            CommentResponse commentResponse = CommentResponse.from(expected);

            mockRestServiceServer.expect(
                    requestTo(simpleBoardProperty.baseUrl() + SimpleBoardUrls.REQUEST_COMMENTS_URL + "?page=9999")
            ).andRespond(withSuccess(
                    objectMapper.writeValueAsString(commentResponse),
                    MediaType.APPLICATION_JSON
            ));

            List<CommentDto> actual = commentManagementService.findComments();

            assertThat(actual).isEqualTo(expected);
            mockRestServiceServer.verify();
        }

        @DisplayName("게시판 서비스에서 특정 댓글 반환 테스트")
        @Test
        void testFindComment() throws JsonProcessingException {
            String content = "test content";
            CommentDto expected = TestDataGenerator.generateCommentDto(content);
            long commentId = 1L;

            mockRestServiceServer.expect(
                    requestTo(simpleBoardProperty.baseUrl() + SimpleBoardUrls.REQUEST_COMMENTS_URL + "/" + commentId)
            ).andRespond(withSuccess(
                    objectMapper.writeValueAsString(expected),
                    MediaType.APPLICATION_JSON
            ));

            CommentDto actual = commentManagementService.findComment(commentId);

            assertThat(actual).isEqualTo(expected);
            mockRestServiceServer.verify();
        }

        @DisplayName("게시판 서비스에서 특정 댓글 삭제 테스트")
        @Test
        void testDeleteComment() {
            long commentId = 1L;

            mockRestServiceServer.expect(
                            requestTo(simpleBoardProperty.baseUrl() + SimpleBoardUrls.REQUEST_COMMENTS_URL + "/" + commentId)
                    ).andExpect(method(HttpMethod.POST))
                    .andRespond(withSuccess());

            assertThatCode(() -> commentManagementService.deleteComment(commentId))
                    .doesNotThrowAnyException();

            mockRestServiceServer.verify();
        }
    }
}