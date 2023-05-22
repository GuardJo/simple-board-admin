package com.guardjo.simpleboard.admin.service;

import com.guardjo.simpleboard.admin.model.MemberDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class MemberManagementService {
    public List<MemberDto> findMembers() {
        log.info("Find Members");

        return List.of();
    }

    public MemberDto findMember(long memberId) {
        log.info("Find Member, memberId = {}", memberId);

        return null;
    }
}
