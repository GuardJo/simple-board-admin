package com.guardjo.simpleboard.admin.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.guardjo.simpleboard.admin.config.SimpleBoardProperty;
import com.guardjo.simpleboard.admin.domain.constant.SimpleBoardUrls;
import com.guardjo.simpleboard.admin.model.MemberDto;
import com.guardjo.simpleboard.admin.model.response.MemberResponse;
import com.guardjo.simpleboard.admin.util.TestDateGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.client.MockRestServiceServer;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@ActiveProfiles("test")
class MemberManagementServiceTest {
    @DisplayName("실제 서비스 연동 테스트")
    @SpringBootTest
    @Nested
    class RealServiceTest {
        private final MemberManagementService memberManagementService;

        @Autowired
        RealServiceTest(MemberManagementService memberManagementService) {
            this.memberManagementService = memberManagementService;
        }

        @DisplayName("게시판 서비스에서 계정 목록 호출 테스트")
        @Test
        void testFindMembers() {
            List<MemberDto> actual = memberManagementService.findMembers();

            assertThat(actual).isNotNull();

            actual.forEach(memberDto -> System.out.println(memberDto.toString()));
        }

        @DisplayName("게시판 서비스에서 특정 계정 호출 테스트")
        @Test
        void testFindMember() {
            long memberId = 1L;

            MemberDto actual = memberManagementService.findMember(memberId);

            assertThat(actual).isNotNull();

            System.out.println(actual.toString());
        }
    }
    
    @DisplayName("외부 서비스 호출 테스트")
    @RestClientTest(MemberManagementService.class)
    @AutoConfigureWebClient(registerRestTemplate = true)
    @EnableConfigurationProperties(SimpleBoardProperty.class)
    @Nested
    class RestTemplateTest {
        private final SimpleBoardProperty simpleBoardProperty;
        private final ObjectMapper objectMapper;
        private final MockRestServiceServer mockRestServiceServer;
        private final MemberManagementService memberManagementService;

        @Autowired
        RestTemplateTest(SimpleBoardProperty simpleBoardProperty,
                         ObjectMapper objectMapper,
                         MockRestServiceServer mockRestServiceServer,
                         MemberManagementService memberManagementService) {
            this.simpleBoardProperty = simpleBoardProperty;
            this.objectMapper = objectMapper;
            this.mockRestServiceServer = mockRestServiceServer;
            this.memberManagementService = memberManagementService;
        }

        @DisplayName("게시판 서비스의 계정 목록 호출 테스트")
        @Test
        void testFindMembers() throws JsonProcessingException {
            List<MemberDto> expected = List.of(TestDateGenerator.generateMemberDto("test@mail.com"));
            MemberResponse memberResponse = MemberResponse.from(expected);

            mockRestServiceServer.expect(
                    requestTo(simpleBoardProperty.baseUrl() + SimpleBoardUrls.REQUEST_MEMBERS_URL + "?page=9999")
            ).andRespond(withSuccess(
                    objectMapper.writeValueAsString(memberResponse),
                    MediaType.APPLICATION_JSON
            ));

            List<MemberDto> actual = memberManagementService.findMembers();

            assertThat(actual.get(0)).isEqualTo(expected.get(0));

            mockRestServiceServer.verify();
        }

        @DisplayName("게시판 서비스의 특정 계정 호출 테스트")
        @Test
        void testFindMember() throws JsonProcessingException {
            long memberId = 1L;
            MemberDto expected = TestDateGenerator.generateMemberDto("test@mail.com");

            mockRestServiceServer.expect(
                    requestTo(simpleBoardProperty.baseUrl() + SimpleBoardUrls.REQUEST_MEMBERS_URL + "/" + memberId)
            ).andRespond(withSuccess(
                    objectMapper.writeValueAsString(expected),
                    MediaType.APPLICATION_JSON
            ));

            MemberDto actual = memberManagementService.findMember(memberId);

            assertThat(actual).isEqualTo(expected);

            mockRestServiceServer.verify();
        }
    }
}