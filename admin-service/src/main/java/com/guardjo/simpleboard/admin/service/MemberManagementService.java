package com.guardjo.simpleboard.admin.service;

import com.guardjo.simpleboard.admin.config.SimpleBoardProperty;
import com.guardjo.simpleboard.admin.domain.constant.SimpleBoardUrls;
import com.guardjo.simpleboard.admin.model.MemberDto;
import com.guardjo.simpleboard.admin.model.response.MemberResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityNotFoundException;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberManagementService {
    private final SimpleBoardProperty simpleBoardProperty;
    private final RestTemplate restTemplate;

    public List<MemberDto> findMembers() {
        log.info("Find Members");

        URI uri = UriComponentsBuilder.fromHttpUrl(getRequestUrl())
                .queryParam("size", Integer.MAX_VALUE)
                .build().toUri();

        MemberResponse response = restTemplate.getForObject(uri, MemberResponse.class);

        return Optional.ofNullable(response)
                .orElse(MemberResponse.empty())
                .getMembers();
    }

    public MemberDto findMember(long memberId) {
        log.info("Find Member, memberId = {}", memberId);

        URI uri = UriComponentsBuilder.fromHttpUrl(getRequestUrl())
                .path("/{memberId}")
                .build(memberId);

        MemberDto memberDto = restTemplate.getForObject(uri, MemberDto.class);

        return Optional.ofNullable(memberDto)
                .orElseThrow(() -> new EntityNotFoundException("Not Found Member, memberId = " + memberId));
    }

    private String getRequestUrl() {
        return simpleBoardProperty.baseUrl() + SimpleBoardUrls.REQUEST_MEMBERS_URL;
    }
}
