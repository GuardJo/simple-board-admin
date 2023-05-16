package com.guardjo.simpleboard.admin.model;

import java.time.LocalDateTime;
import java.util.Set;

public record ArticleDto(
        String creator,
        LocalDateTime createTime,
        String modifier,
        LocalDateTime modifiedTime,
        String title,
        String content
) {
    public static ArticleDto of(
                      String creator,
                      LocalDateTime createTime,
                      String modifier,
                      LocalDateTime modifiedTime,
                      String title,
                      String content) {
        return new ArticleDto(creator, createTime, modifier, modifiedTime, title, content);
    }
}
