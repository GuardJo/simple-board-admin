package com.guardjo.simpleboard.admin.model;

import java.time.LocalDateTime;

public record MemberDto(
        String email,
        String name,
        String creator,
        LocalDateTime createTime,
        String modifier,
        LocalDateTime modifiedTime
) {
    public static MemberDto of(String email, String name, String creator, LocalDateTime createTime, String modifier, LocalDateTime modifiedTime) {
        return new MemberDto(email, name, creator, createTime, modifier, modifiedTime);
    }
}
