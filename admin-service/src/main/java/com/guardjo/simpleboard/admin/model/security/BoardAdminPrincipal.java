package com.guardjo.simpleboard.admin.model.security;

import com.guardjo.simpleboard.admin.domain.constant.RoleType;
import com.guardjo.simpleboard.admin.model.AdminAccountDto;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public record BoardAdminPrincipal(String email, String name, String password,
                                  Collection<? extends GrantedAuthority> authorities,
                                  Map<String, Object> oAuth2Attributes) implements UserDetails, OAuth2User {
    public static BoardAdminPrincipal of(String email, String name, String password, Set<RoleType> roleTypes) {
        return new BoardAdminPrincipal(
                email,
                name,
                password,
                roleTypes.stream()
                        .map(RoleType::name)
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toUnmodifiableSet()),
                Map.of());
    }

    public static BoardAdminPrincipal of(String email, String name, String password, Set<RoleType> roleTypes, Map<String, Object> oAuth2Attributes) {
        return new BoardAdminPrincipal(
                email,
                name,
                password,
                roleTypes.stream()
                        .map(RoleType::name)
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toUnmodifiableSet()),
                oAuth2Attributes);
    }

    public static BoardAdminPrincipal from(AdminAccountDto adminAccountDto) {
        return BoardAdminPrincipal.of(
                adminAccountDto.email(),
                adminAccountDto.name(),
                adminAccountDto.password(),
                adminAccountDto.roleTypes()
        );
    }

    public static AdminAccountDto toDto(BoardAdminPrincipal principal) {
        return AdminAccountDto.of(
                null,
                principal.getUsername(),
                principal.getNickName(),
                principal.getPassword(),
                principal.authorities.stream()
                        .map(GrantedAuthority::getAuthority)
                        .map(RoleType::valueOf)
                        .collect(Collectors.toSet()),
                principal.getNickName(),
                LocalDateTime.now()
        );
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    // Oauth2 관련 메소드
    @Override
    public Map<String, Object> getAttributes() {
        return this.oAuth2Attributes;
    }

    @Override
    public String getName() {
        return this.email;
    }

    public String getNickName() {
        return this.name;
    }
}
