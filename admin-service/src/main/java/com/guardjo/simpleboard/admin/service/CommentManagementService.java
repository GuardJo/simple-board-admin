package com.guardjo.simpleboard.admin.service;

import com.guardjo.simpleboard.admin.model.CommentDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CommentManagementService {
    public List<CommentDto> findComments() {
        log.info("Finding Comment List");
        // TODO 기능 구현 필요
        return List.of();
    }

    public CommentDto findComment(long commentId) {
        log.info("Finding Comment, commentId = {}", commentId);
        // TODO 기능 구현 필요
        return null;
    }

    public void deleteComment(long commentId) {
        log.info("Deleting Comment, commentId = {}", commentId);
        // TODO 기능 구현 필요
    }
}
