package com.guardjo.simpleboard.admin.controller;

import com.guardjo.simpleboard.admin.config.TestSecurityConfig;
import com.guardjo.simpleboard.admin.controller.constant.UrlConstant;
import com.guardjo.simpleboard.admin.model.MemberDto;
import com.guardjo.simpleboard.admin.service.MemberManagementService;
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

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Import(TestSecurityConfig.class)
@WebMvcTest(MemberManagementController.class)
class MemberManagementControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Mock
    private MemberManagementService memberManagementService;

    @DisplayName("게시판 회원 관리 뷰페이지 반환 테스트")
    @Test
    @WithMockUser(username = "test@mail.com")
    void testGetMemberManagementView() throws Exception {
        List<MemberDto> memberDtos = List.of(TestDataGenerator.generateMemberDto("test@mail.com"));
        given(memberManagementService.findMembers()).willReturn(memberDtos);

        mockMvc.perform(get(UrlConstant.MEMBER_MANAGEMENT_URL_PREFIX))
                .andExpect(status().isOk())
                .andExpect(view().name("management/member"))
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(model().attribute("members", memberDtos));

        then(memberManagementService).should().findMembers();
    }

    @DisplayName("게시판 회원 관리 뷰페이지 내 특정 회원 반환 테스트")
    @Test
    @WithMockUser(username = "test@mail.com")
    void testGetMemberDataInView() throws Exception {
        long memberId = 1L;
        MemberDto memberDto = TestDataGenerator.generateMemberDto("test@mail.com");

        given(memberManagementService.findMember(memberId)).willReturn(memberDto);

        mockMvc.perform(get(UrlConstant.MEMBER_MANAGEMENT_URL_PREFIX + "/" + memberId))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.email").value(memberDto.email()))
                .andExpect(jsonPath("$.name").value(memberDto.name()));

        then(memberManagementService).should().findMember(memberId);
    }
}