package com.guardjo.simpleboard.admin.util;

import com.guardjo.simpleboard.admin.domain.constant.RoleType;
import com.guardjo.simpleboard.admin.model.AdminAccountDto;
import com.guardjo.simpleboard.admin.model.ArticleDto;
import com.guardjo.simpleboard.admin.model.CommentDto;
import com.guardjo.simpleboard.admin.model.MemberDto;
import com.guardjo.simpleboard.admin.model.response.ArticleResponse;
import com.guardjo.simpleboard.admin.model.response.CommentResponse;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public class TestDataGenerator {
    public static ArticleDto generateArticleDto(String title, String content) {
        return ArticleDto.of(
                1L,
                "creator",
                LocalDateTime.now(),
                "modifier",
                LocalDateTime.now(),
                title,
                content
        );
    }

    public static AdminAccountDto generateAccountDto() {
        return AdminAccountDto.of(
                1L,
                "test@mail.com",
                "tester",
                "1234",
                Set.of(RoleType.DEVELOPER),
                "tester",
                LocalDateTime.now()
        );
    }

    public static ArticleResponse generateArticleResponse(List<ArticleDto> articleDtoList) {
        return ArticleResponse.from(articleDtoList);
    }

    public static CommentDto generateCommentDto(String content) {
        return CommentDto.of(
                null,
                "tester",
                LocalDateTime.now(),
                "tester",
                LocalDateTime.now(),
                content
        );
    }

    public static CommentResponse generateCommentResponse(List<CommentDto> commentDtos) {
        return CommentResponse.from(commentDtos);
    }

    public static MemberDto generateMemberDto(String email) {
        return MemberDto.of(
                email,
                "tester",
                "tester",
                LocalDateTime.now(),
                "tester",
                LocalDateTime.now()
        );
    }
}
