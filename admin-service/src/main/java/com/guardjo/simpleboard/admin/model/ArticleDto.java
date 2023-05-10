package com.guardjo.simpleboard.admin.model;

import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@AllArgsConstructor(staticName = "of")
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
}
