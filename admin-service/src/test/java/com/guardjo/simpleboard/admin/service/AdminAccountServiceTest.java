package com.guardjo.simpleboard.admin.service;

import com.guardjo.simpleboard.admin.domain.AdminAccount;
import com.guardjo.simpleboard.admin.domain.constant.RoleType;
import com.guardjo.simpleboard.admin.model.AdminAccountDto;
import com.guardjo.simpleboard.admin.repository.AdminAccountRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.mockito.BDDMockito.*;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class AdminAccountServiceTest {
    @Mock
    private AdminAccountRepository adminAccountRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private AdminAccountService adminAccountService;

    @DisplayName("관리자 계정 조회 테스트 (email) : 정상")
    @Test
    void testSearchAdminAccount() {
        String email = "test@mail.com";
        AdminAccount entity = AdminAccount.of(email, "tester", Set.of(), "1234");
        AdminAccountDto expected = AdminAccountDto.from(entity);

        given(adminAccountRepository.findByEmail(email)).willReturn(Optional.of(entity));

        AdminAccountDto actual = adminAccountService.searchAdminAccount(email).orElseThrow();

        assertThat(actual).isEqualTo(expected);

        then(adminAccountRepository).should().findByEmail(email);
    }

    @DisplayName("전체 관리자 계정 목록 조회 테스트 : 정상")
    @Test
    void testFindAdminAcounts() {
        String email = "test@mail.com";
        AdminAccount entity = AdminAccount.of(email, "tester", Set.of(), "1234");
        AdminAccountDto expected = AdminAccountDto.from(entity);

        given(adminAccountRepository.findAll()).willReturn(List.of(entity));

        List<AdminAccountDto> actual = adminAccountService.findAdminAccounts();

        assertThat(actual.get(0)).isEqualTo(expected);

        then(adminAccountRepository).should().findAll();
    }

    @DisplayName("신규 관리자 계정 생성 테스트 : 정상")
    @Test
    void testCreateAdminAcount() {
        String email = "test@mail.com";
        String name = "tester";
        String password = "1234";

        AdminAccount entity = AdminAccount.of(email, name, Set.of(RoleType.MANAGER), password);
        AdminAccountDto expected = AdminAccountDto.from(entity);

        given(adminAccountRepository.save(entity)).willReturn(entity);
        given(passwordEncoder.encode(password)).willReturn(password);

        AdminAccountDto actual = adminAccountService.createAdminAccount(email, name, password);

        assertThat(actual).isEqualTo(expected);

        then(adminAccountRepository).should().save(entity);
    }

    @DisplayName("특정 관리자 계정 삭제 테스트 : 정상")
    @Test
    void testDeleteAdminAccount() {
        String email = "test@mail.com";

        willDoNothing().given(adminAccountRepository).deleteByEmail(email);

        assertThatCode(() -> adminAccountService.deleteAdminAccount(email))
                .doesNotThrowAnyException();

        then(adminAccountRepository).should().deleteByEmail(email);
    }
}