package com.guardjo.simpleboard.admin.model;

import com.guardjo.simpleboard.admin.domain.AdminAccount;
import com.guardjo.simpleboard.admin.domain.constant.RoleType;

import java.util.Set;

/**
 * A DTO for the {@link AdminAccount} entity
 */
public record AdminAccountDto(String email, String name, String password, Set<RoleType> roleTypes) {
    public static AdminAccountDto of(String email, String name, String password, Set<RoleType> roleTypes) {
        return new AdminAccountDto(email, name, password, roleTypes);
    }

    public static AdminAccountDto from(AdminAccount account) {
        return AdminAccountDto.of(
                account.getEmail(),
                account.getName(),
                account.getPassword(),
                account.getRoleTypes()
        );
    }

    public static AdminAccount toEntity(AdminAccountDto adminAccountDto) {
        return AdminAccount.of(
                adminAccountDto.email(),
                adminAccountDto.name(),
                adminAccountDto.roleTypes(),
                adminAccountDto.password()
        );
    }
}