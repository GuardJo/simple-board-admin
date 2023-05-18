package com.guardjo.simpleboard.admin.service;

import com.guardjo.simpleboard.admin.model.AdminAccountDto;
import com.guardjo.simpleboard.admin.repository.AdminAccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
public class AdminAccountService {
    private final AdminAccountRepository adminAccountRepository;

    public AdminAccountService(AdminAccountRepository adminAccountRepository) {
        this.adminAccountRepository = adminAccountRepository;
    }

    /**
     * 주어진 이메일에 해당하는 관리자 계정을 반환한다.
     * @param email 조회할 계정의 이메일
     * @return 조회결과 객체
     */
    public Optional<AdminAccountDto> searchAdminAccount(String email) {
        log.info("Finding AdminAccount, email = {}", email);

        return Optional.empty();
    }

    /**
     * 저장된 전체 관리자 계정 목록을 반환한다.
     * @return 저장된 전체 관리자 계정 목록
     */
    public List<AdminAccountDto> findAdminAccounts() {
        log.info("Finding AdminAccounts");

        return List.of();
    }

    /**
     * accountId에 해당하는 관리자 계정을 삭제한다.
     * @param accountId 삭제할 관리자 계정의 식별키
     */
    public void deleteAdminAccount(long accountId) {
        log.info("Deleting AdminAccount, accountId = {}", accountId);
    }

    /**
     * 관리자 계정을 생성한다.
     * @param email 관리자 계정 메일
     * @param name 관리자 계정명
     * @param password 관리자 계정 비밀번호
     * @return 생성된 관리자 계정
     */
    public AdminAccountDto createAdminAccount(String email, String name, String password) {
        log.info("Creating AdminAccount, email = {}", email);
        
        return null;
    }
}
