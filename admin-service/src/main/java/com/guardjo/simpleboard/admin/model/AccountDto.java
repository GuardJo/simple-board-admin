package com.guardjo.simpleboard.admin.model;

import com.guardjo.simpleboard.admin.domain.Account;
import com.guardjo.simpleboard.admin.domain.constant.RoleType;

import java.util.Set;

/**
 * A DTO for the {@link com.guardjo.simpleboard.admin.domain.Account} entity
 */
public record AccountDto(String email, String name, String password, Set<RoleType> roleTypes) {
    public static AccountDto of(String email, String name, String password, Set<RoleType> roleTypes) {
        return new AccountDto(email, name, password, roleTypes);
    }

    public static AccountDto from(Account account) {
        return AccountDto.of(
                account.getEmail(),
                account.getName(),
                account.getPassword(),
                account.getRoleTypes()
        );
    }

    public static Account toEntity(AccountDto accountDto) {
        return Account.of(
                accountDto.email(),
                accountDto.name(),
                accountDto.roleTypes(),
                accountDto.password()
        );
    }
}