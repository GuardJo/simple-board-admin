package com.guardjo.simpleboard.admin.model;

import java.time.LocalDateTime;

public record CommentDto(
        Long id,
        Long parentCommentId,
        String creator,
        LocalDateTime createTime,
        String modifier,
        LocalDateTime modifiedTime,
        String content
) {
    public static CommentDto of(Long id, Long parentCommentId, String creator, LocalDateTime createTime, String modifier, LocalDateTime modifiedTime, String content) {
        return new CommentDto(id, parentCommentId, creator, createTime, modifier, modifiedTime, content);
    }
}
