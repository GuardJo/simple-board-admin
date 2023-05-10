package com.guardjo.simpleboard.admin.model;

import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

public record ArticleDto(
        Long id,
        AccountDto member,
        String creator,
        LocalDateTime createTime,
        String modifier,
        LocalDateTime modifiedTime,
        String content,
        Set<String> hashtags
) {
    public static ArticleDto of(Long id,
                      AccountDto member,
                      String creator,
                      LocalDateTime createTime,
                      String modifier,
                      LocalDateTime modifiedTime,
                      String content,
                      Set<String> hashtags) {
        return new ArticleDto(id, member, creator, createTime, modifier, modifiedTime, content, hashtags);
    }
}
