package com.guardjo.simpleboard.admin.repository;

import com.guardjo.simpleboard.admin.config.TestConfig;
import com.guardjo.simpleboard.admin.domain.Account;
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

@ActiveProfiles("test")
@Import(TestConfig.class)
@DataJpaTest
class JpaRepositoryTest {
    private static final long TEST_DATA_SIZE = 5L;
    private final AccountRepository accountRepository;

    @Autowired
    JpaRepositoryTest(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @DisplayName("회원 정보 저장 테스트")
    @Test
    void testSaveAccount() {
        Account newAccount = Account.of(
                "tester@mail.com",
                "tester",
                Set.of(RoleType.USER),
                "1234"
        );

        Account saveAccount = accountRepository.save(newAccount);

        assertThat(saveAccount.getId()).isNotNull();
    }

    @DisplayName("특정 회원 정보 반환 테스트")
    @Test
    void testReadAccount() {
        Long id = 1L;

        Account account = accountRepository.findById(id).orElseThrow();

        assertThat(account)
                .hasFieldOrPropertyWithValue("email", "test_admin@mail.com");
    }

    @DisplayName("전체 회원 정보 반환 테스트")
    @Test
    void testReadAccounts() {
        List<Account> accounts = accountRepository.findAll();

        assertThat(accounts.size()).isEqualTo(TEST_DATA_SIZE);
    }

    @DisplayName("특정 회원 정보 업데이트 테스트")
    @Test
    void testUpdateAccount() {
        String updateName = "updater";
        Account oldAccount = accountRepository.findById(1L).orElseThrow();

        oldAccount.setName(updateName);

        accountRepository.flush();

        Account newAccount = accountRepository.findById(oldAccount.getId()).orElseThrow();

        assertThat(newAccount)
                .hasFieldOrPropertyWithValue("name", updateName);
    }

    @DisplayName("특정 회원 정보 삭제 테스트")
    @Test
    void testDeleteAccount() {
        Long id = 1L;

        accountRepository.deleteById(id);

        Account deletedAccount = accountRepository.findById(id).orElse(null);

        assertThat(deletedAccount).isNull();
        assertThat(accountRepository.count()).isEqualTo(TEST_DATA_SIZE - 1);
    }

    @DisplayName("전체 회원 정보 삭제 테스트")
    @Test
    void testDeleteAccounts() {
        accountRepository.deleteAll();

        assertThat(accountRepository.count()).isEqualTo(0);
    }
}