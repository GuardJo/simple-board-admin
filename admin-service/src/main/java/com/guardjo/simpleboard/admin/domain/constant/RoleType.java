package com.guardjo.simpleboard.admin.domain.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.NoSuchElementException;

@Getter
@RequiredArgsConstructor
public enum RoleType {
    USER("ROLE_USER", "사용자"),
    MANAGER("ROLE_MANAGER", "운영자"),
    DEVELOPER("ROLE_DEVELOPER", "개발자"),
    ADMIN("ROLE_ADMIN", "관리자");

    private final String roleName;
    private final String description;
    public static RoleType getRoleType(String description) {
        return Arrays.stream(RoleType.values())
                .filter(roleType -> roleType.getDescription().equals(description))
                .findFirst().orElseThrow(() -> new NoSuchElementException("No such RoleType, description = " + description));
    }
}
