package com.guardjo.simpleboard.admin.util;

import com.guardjo.simpleboard.admin.domain.constant.RoleType;
import com.guardjo.simpleboard.admin.model.AccountDto;
import com.guardjo.simpleboard.admin.model.ArticleDto;
import com.guardjo.simpleboard.admin.model.response.ArticleResponse;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public class TestDateGenerator {
    public static ArticleDto generateArticleDto(String title, String content) {
        return ArticleDto.of(
                1L,
                generateAccountDto(),
                "creator",
                LocalDateTime.now(),
                "modifier",
                LocalDateTime.now(),
                title,
                content,
                Set.of("test")
        );
    }

    public static AccountDto generateAccountDto() {
        return AccountDto.of(
                "test@mail.com",
                "tester",
                "1234",
                Set.of(RoleType.DEVELOPER)
        );
    }

    public static ArticleResponse generateArticleResponse(List<ArticleDto> articleDtoList) {
        return ArticleResponse.from(articleDtoList);
    }
}
