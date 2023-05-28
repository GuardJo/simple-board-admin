package com.guardjo.simpleboard.admin.controller;

import com.guardjo.simpleboard.admin.controller.constant.UrlConstant;
import com.guardjo.simpleboard.admin.model.CommentDto;
import com.guardjo.simpleboard.admin.service.CommentManagementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(UrlConstant.COMMENT_MANAGEMENT_URL_PREFIX)
@Controller
@Slf4j
@RequiredArgsConstructor
public class CommentManagementController {
    private final CommentManagementService commentManagementService;

    @GetMapping
    public String getDefaultViewPage(Model model) {
        log.info("Request Default Comment Management Page");

        model.addAttribute("comments", commentManagementService.findComments());

        return "management/comment";
    }

    @ResponseBody
    @GetMapping("/{commentId}")
    public CommentDto getComment(@PathVariable long commentId) {
        log.info("Request Get Comment, commentId = {}", commentId);

        return commentManagementService.findComment(commentId);
    }

    @DeleteMapping("/{commentId}")
    public String deleteComment(@PathVariable long commentId) {
        log.info("Request Delete Comment, commentId = {}", commentId);

        commentManagementService.deleteComment(commentId);

        return "redirect:" + UrlConstant.COMMENT_MANAGEMENT_URL_PREFIX;
    }
}
