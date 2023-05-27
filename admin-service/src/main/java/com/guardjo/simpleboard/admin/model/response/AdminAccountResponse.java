package com.guardjo.simpleboard.admin.model.response;

import com.guardjo.simpleboard.admin.domain.constant.RoleType;
import com.guardjo.simpleboard.admin.model.AdminAccountDto;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

public record AdminAccountResponse(
        Long id,
        String email,
        String name,
        String roleTypes,
        String creator,
        LocalDateTime createTime
) {
    public static AdminAccountResponse of(Long id, String email, String name, String roleTypes, String creator, LocalDateTime createTime) {
        return new AdminAccountResponse(id, email, name, roleTypes, creator, createTime);
    }

    public static AdminAccountResponse from(AdminAccountDto dto) {
        return AdminAccountResponse.of(
                dto.id(),
                dto.email(),
                dto.name(),
                dto.roleTypes().stream()
                        .map(RoleType::getDescription)
                        .collect(Collectors.joining(",")),
                dto.creator(),
                dto.createTime()
        );
    }
}
