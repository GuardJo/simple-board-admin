package com.guardjo.simpleboard.admin.model.response;

import com.guardjo.simpleboard.admin.domain.constant.RoleType;
import com.guardjo.simpleboard.admin.model.AdminAccountDto;

import java.util.stream.Collectors;

public record AdminAccountResponse(
        String email,
        String name,
        String roleTypes
) {
    public static AdminAccountResponse of(String email, String name, String roleTypes) {
        return AdminAccountResponse.of(email, name, roleTypes);
    }

    public static AdminAccountResponse from(AdminAccountDto dto) {
        return AdminAccountResponse.of(
                dto.email(),
                dto.name(),
                dto.roleTypes().stream()
                        .map(RoleType::getDescription)
                        .collect(Collectors.joining(", "))
        );
    }
}
