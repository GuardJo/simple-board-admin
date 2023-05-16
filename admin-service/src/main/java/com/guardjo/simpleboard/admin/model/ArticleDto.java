package com.guardjo.simpleboard.admin.model;

import java.time.LocalDateTime;
import java.util.Set;

public record ArticleDto(
        Long id,
        AdminAccountDto member,
        String creator,
        LocalDateTime createTime,
        String modifier,
        LocalDateTime modifiedTime,
        String title,
        String content,
        Set<String> hashtags
) {
    public static ArticleDto of(Long id,
                      AdminAccountDto member,
                      String creator,
                      LocalDateTime createTime,
                      String modifier,
                      LocalDateTime modifiedTime,
                      String title,
                      String content,
                      Set<String> hashtags) {
        return new ArticleDto(id, member, creator, createTime, modifier, modifiedTime, title, content, hashtags);
    }
}
