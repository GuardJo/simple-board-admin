package com.guardjo.simpleboard.admin.service;

import com.guardjo.simpleboard.admin.config.SimpleBoardProperty;
import com.guardjo.simpleboard.admin.domain.constant.SimpleBoardUrls;
import com.guardjo.simpleboard.admin.model.CommentDto;
import com.guardjo.simpleboard.admin.model.response.CommentResponse;
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
public class CommentManagementService {
    private final SimpleBoardProperty simpleBoardProperty;
    private final RestTemplate restTemplate;

    public List<CommentDto> findComments() {
        log.info("Finding Comment List");

        URI uri = UriComponentsBuilder.fromHttpUrl(
                        simpleBoardProperty.baseUrl() + SimpleBoardUrls.REQUEST_COMMENTS_URL
                ).queryParam("size", Integer.MAX_VALUE)
                .build().toUri();

        CommentResponse response = restTemplate.getForObject(uri, CommentResponse.class);

        return Optional.ofNullable(response)
                .orElse(CommentResponse.empty())
                .getComments();
    }

    public CommentDto findComment(long commentId) {
        log.info("Finding Comment, commentId = {}", commentId);

        URI uri = UriComponentsBuilder.fromHttpUrl(
                        simpleBoardProperty.baseUrl() + SimpleBoardUrls.REQUEST_COMMENTS_URL
                ).path("/{commentId}")
                .build(commentId);

        CommentDto commentDto = restTemplate.getForObject(uri, CommentDto.class);

        return Optional.ofNullable(commentDto)
                .orElseThrow(() -> new EntityNotFoundException("Not Found Comment, commentId = " + commentDto));
    }

    public void deleteComment(long commentId) {
        log.info("Deleting Comment, commentId = {}", commentId);

        URI uri = UriComponentsBuilder.fromHttpUrl(
                        simpleBoardProperty.baseUrl() + SimpleBoardUrls.REQUEST_COMMENTS_URL
                ).path("/{commentId}")
                .build(commentId);

        restTemplate.delete(uri);

        log.info("Deleted Comment, commentId = {}", commentId);
    }
}
