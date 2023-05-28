package com.guardjo.simpleboard.admin.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public record MemberDto(
        @JsonProperty("memberId")
        Long id,
        String email,
        String name,
        String creator,
        LocalDateTime createTime,
        String modifier,
        LocalDateTime modifiedTime
) {
    public static MemberDto of(Long id, String email, String name, String creator, LocalDateTime createTime, String modifier, LocalDateTime modifiedTime) {
        return new MemberDto(id, email, name, creator, createTime, modifier, modifiedTime);
    }
}
