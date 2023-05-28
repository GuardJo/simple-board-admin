package com.guardjo.simpleboard.admin.model;

import com.guardjo.simpleboard.admin.domain.AdminAccount;
import com.guardjo.simpleboard.admin.domain.constant.RoleType;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * A DTO for the {@link AdminAccount} entity
 */
public record AdminAccountDto(
        Long id,
        String email,
        String name,
        String password,
        Set<RoleType> roleTypes,
        String creator,
        LocalDateTime createTime
) {
    public static AdminAccountDto of(Long id, String email, String name, String password, Set<RoleType> roleTypes, String creator, LocalDateTime createTime) {
        return new AdminAccountDto(
                id,
                email,
                name,
                password,
                roleTypes,
                creator,
                createTime
        );
    }

    public static AdminAccountDto from(AdminAccount account) {
        return AdminAccountDto.of(
                account.getId(),
                account.getEmail(),
                account.getName(),
                account.getPassword(),
                account.getRoleTypes(),
                account.getCreator(),
                account.getCreateTime()
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