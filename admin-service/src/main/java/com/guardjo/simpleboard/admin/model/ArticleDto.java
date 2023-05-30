package com.guardjo.simpleboard.admin.model;

import java.time.LocalDateTime;

public record ArticleDto(
        long id,
        String creator,
        LocalDateTime createTime,
        String modifier,
        LocalDateTime modifiedTime,
        String title,
        String content
) {
    public static ArticleDto of(
            long id,
            String creator,
            LocalDateTime createTime,
            String modifier,
            LocalDateTime modifiedTime,
            String title,
            String content) {
        return new ArticleDto(id, creator, createTime, modifier, modifiedTime, title, content);
    }
}
