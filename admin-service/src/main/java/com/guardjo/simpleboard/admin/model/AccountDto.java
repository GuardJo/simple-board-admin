package com.guardjo.simpleboard.admin.model;

import com.guardjo.simpleboard.admin.domain.Account;

/**
 * A DTO for the {@link com.guardjo.simpleboard.admin.domain.Account} entity
 */
public record AccountDto(String email, String name, String password) {
    public static AccountDto of(String email, String name, String password) {
        return new AccountDto(email, name, password);
    }

    public static AccountDto from(Account account) {
        return AccountDto.of(
                account.getEmail(),
                account.getName(),
                account.getPassword()
        );
    }

    public static Account toEntity(AccountDto accountDto) {
        return Account.of(
                accountDto.email,
                accountDto.name,
                accountDto.password
        );
    }
}