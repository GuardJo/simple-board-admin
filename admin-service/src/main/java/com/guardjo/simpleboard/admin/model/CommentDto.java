package com.guardjo.simpleboard.admin.model;

import java.time.LocalDateTime;

public record CommentDto(
        Long parentCommentId,
        String creator,
        LocalDateTime createTime,
        String modifier,
        LocalDateTime modifiedTime,
        String content
) {
    public static CommentDto of(Long parentCommentId, String creator, LocalDateTime createTime, String modifier, LocalDateTime modifiedTime, String content) {
        return new CommentDto(parentCommentId, creator, createTime, modifier, modifiedTime, content);
    }
}
