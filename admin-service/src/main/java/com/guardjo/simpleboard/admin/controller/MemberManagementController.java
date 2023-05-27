package com.guardjo.simpleboard.admin.controller;

import com.guardjo.simpleboard.admin.controller.constant.UrlConstant;
import com.guardjo.simpleboard.admin.model.MemberDto;
import com.guardjo.simpleboard.admin.service.MemberManagementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping(UrlConstant.MEMBER_MANAGEMENT_URL_PREFIX)
@Controller
@Slf4j
@RequiredArgsConstructor
public class MemberManagementController {
    private final MemberManagementService memberManagementService;

    @GetMapping
    public String getDefaultViewPage(Model model) {
        log.info("Request Default Member Management Page");

        model.addAttribute("members", memberManagementService.findMembers());

        return "management/member";
    }

    @ResponseBody
    @GetMapping("/{memberId}")
    public MemberDto getMember(@PathVariable long memberId) {
        log.info("Request Get Member, memberId = {}", memberId);

        return memberManagementService.findMember(memberId);
    }
}
