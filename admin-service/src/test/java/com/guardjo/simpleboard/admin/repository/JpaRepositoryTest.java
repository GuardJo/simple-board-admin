package com.guardjo.simpleboard.admin.repository;

import com.guardjo.simpleboard.admin.config.TestConfig;
import com.guardjo.simpleboard.admin.domain.AdminAccount;
import com.guardjo.simpleboard.admin.domain.constant.RoleType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

@ActiveProfiles("test")
@Import(TestConfig.class)
@DataJpaTest
class JpaRepositoryTest {
    private static final long TEST_DATA_SIZE = 5L;
    private final AdminAccountRepository adminAccountRepository;

    @Autowired
    JpaRepositoryTest(AdminAccountRepository adminAccountRepository) {
        this.adminAccountRepository = adminAccountRepository;
    }

    @DisplayName("회원 정보 저장 테스트")
    @Test
    void testSaveAccount() {
        AdminAccount newAccount = AdminAccount.of(
                "tester@mail.com",
                "tester",
                Set.of(RoleType.USER),
                "1234"
        );

        AdminAccount saveAccount = adminAccountRepository.save(newAccount);

        assertThat(saveAccount.getId()).isNotNull();
    }

    @DisplayName("특정 회원 정보 반환 테스트")
    @Test
    void testReadAccount() {
        Long id = 1L;

        AdminAccount account = adminAccountRepository.findById(id).orElseThrow();

        assertThat(account)
                .hasFieldOrPropertyWithValue("email", "test_admin@mail.com");
    }

    @DisplayName("특정 회원 정보 반환 테스트 (이메일)")
    @Test
    void testReadAccountWithEmail() {
        String email = "test@mail.com";

        AdminAccount account = adminAccountRepository.findByEmail(email).orElseThrow();

        assertThat(account)
                .hasFieldOrPropertyWithValue("email", email)
                .hasFieldOrPropertyWithValue("name", "guardjo");
    }

    @DisplayName("전체 회원 정보 반환 테스트")
    @Test
    void testReadAccounts() {
        List<AdminAccount> accounts = adminAccountRepository.findAll();

        assertThat(accounts.size()).isEqualTo(TEST_DATA_SIZE);
    }

    @DisplayName("특정 회원 정보 업데이트 테스트")
    @Test
    void testUpdateAccount() {
        String updateName = "updater";
        AdminAccount oldAccount = adminAccountRepository.findById(1L).orElseThrow();

        oldAccount.setName(updateName);

        adminAccountRepository.flush();

        AdminAccount newAccount = adminAccountRepository.findById(oldAccount.getId()).orElseThrow();

        assertThat(newAccount)
                .hasFieldOrPropertyWithValue("name", updateName);
    }

    @DisplayName("특정 회원 정보 삭제 테스트")
    @Test
    void testDeleteAccount() {
        Long id = 1L;

        adminAccountRepository.deleteById(id);

        AdminAccount deletedAccount = adminAccountRepository.findById(id).orElse(null);

        assertThat(deletedAccount).isNull();
        assertThat(adminAccountRepository.count()).isEqualTo(TEST_DATA_SIZE - 1);
    }

    @DisplayName("특정 회원 정보 삭제 테스트 (email)")
    @Test
    void testDeleteAccountWithEmail() {
        String email = "test@mail.com";
        long before = adminAccountRepository.count();

        adminAccountRepository.deleteByEmail(email);

        long after = adminAccountRepository.count();

        assertThat(after).isEqualTo(before - 1);
    }

    @DisplayName("전체 회원 정보 삭제 테스트")
    @Test
    void testDeleteAccounts() {
        adminAccountRepository.deleteAll();

        assertThat(adminAccountRepository.count()).isEqualTo(0);
    }
}