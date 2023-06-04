package com.guardjo.simpleboard.admin.service;

import com.guardjo.simpleboard.admin.domain.AdminAccount;
import com.guardjo.simpleboard.admin.domain.constant.RoleType;
import com.guardjo.simpleboard.admin.model.AdminAccountDto;
import com.guardjo.simpleboard.admin.repository.AdminAccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdminAccountService {
    private final AdminAccountRepository adminAccountRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 주어진 이메일에 해당하는 관리자 계정을 반환한다.
     *
     * @param email 조회할 계정의 이메일
     * @return 조회결과 객체
     */
    public Optional<AdminAccountDto> searchAdminAccount(String email) {
        log.info("Finding AdminAccount, email = {}", email);

        Optional<AdminAccount> adminAccount = adminAccountRepository.findByEmail(email);

        return adminAccount.map(AdminAccountDto::from);
    }

    /**
     * 저장된 전체 관리자 계정 목록을 반환한다.
     *
     * @return 저장된 전체 관리자 계정 목록
     */
    public List<AdminAccountDto> findAdminAccounts() {
        log.info("Finding AdminAccounts");

        return adminAccountRepository.findAll().stream()
                .map(AdminAccountDto::from)
                .toList();
    }

    /**
     * accountId에 해당하는 관리자 계정을 삭제한다.
     *
     * @param email 삭제할 관리자 계정 메일
     */
    public void deleteAdminAccount(String email) {
        log.info("Deleting AdminAccount, email = {}", email);

        adminAccountRepository.deleteByEmail(email);

        log.info("Deleted AdminAccount, email, = {}", email);
    }

    /**
     * 관리자 계정을 생성한다.
     *
     * @param email    관리자 계정 메일
     * @param name     관리자 계정명
     * @param password 관리자 계정 비밀번호
     * @return 생성된 관리자 계정
     */
    public AdminAccountDto createAdminAccount(String email, String name, String password) {
        log.info("Creating AdminAccount, email = {}", email);

        String encodedPassword = passwordEncoder.encode(password);
        AdminAccount newAccount = AdminAccount.of(email, name, new LinkedHashSet<>(), encodedPassword);
        newAccount.getRoleTypes().add(RoleType.USER);

        return AdminAccountDto.from(adminAccountRepository.save(newAccount));
    }
}
