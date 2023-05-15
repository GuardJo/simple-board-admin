package com.guardjo.simpleboard.admin.model;

import java.time.LocalDateTime;

public record CommentDto(
        Long id,
        Long articleId,
        Long parentCommentId,
        String creator,
        LocalDateTime createTime,
        String modifier,
        LocalDateTime modifiedTime,
        String content
) {
    public static CommentDto of(Long id, Long articleId, Long parentCommentId, String creator, LocalDateTime createTime, String modifier, LocalDateTime modifiedTime, String content) {
        return new CommentDto(id, articleId, parentCommentId, creator, createTime, modifier, modifiedTime, content);
    }
}
