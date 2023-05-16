package com.guardjo.simpleboard.admin.repository;

import com.guardjo.simpleboard.admin.domain.AdminAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminAccountRepository extends JpaRepository<AdminAccount, Long> {
}