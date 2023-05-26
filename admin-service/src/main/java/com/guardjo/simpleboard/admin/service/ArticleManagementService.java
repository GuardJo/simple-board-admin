package com.guardjo.simpleboard.admin.service;

import com.guardjo.simpleboard.admin.config.SimpleBoardProperty;
import com.guardjo.simpleboard.admin.domain.constant.SimpleBoardUrls;
import com.guardjo.simpleboard.admin.model.ArticleDto;
import com.guardjo.simpleboard.admin.model.response.ArticleResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityNotFoundException;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import static com.guardjo.simpleboard.admin.model.response.ArticleResponse.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class ArticleManagementService {
    private final RestTemplate restTemplate;
    private final SimpleBoardProperty simpleBoardProperty;

    public List<ArticleDto> findArticles() {
        log.info("Finding Articles");
        URI uri = UriComponentsBuilder.fromHttpUrl(simpleBoardProperty.baseUrl() + SimpleBoardUrls.REQUEST_ARTICLES_URL)
                .queryParam("size", Integer.MAX_VALUE)
                .build().toUri();

        ArticleResponse response = restTemplate.getForObject(uri, ArticleResponse.class);

        return Optional.ofNullable(response)
                .orElse(ArticleResponse.empty())
                .getArticles();
    }

    public ArticleDto findArticle(Long articleId) {
        log.info("Finding Article, articleId = {}", articleId);

        URI uri = UriComponentsBuilder.fromHttpUrl(simpleBoardProperty.baseUrl() + SimpleBoardUrls.REQUEST_ARTICLES_URL + "/" + articleId)
                .build()
                .toUri();

        return Optional.ofNullable(restTemplate.getForObject(uri, ArticleDto.class))
                .orElseThrow(() -> new EntityNotFoundException("Not Found Article , articleId = " + articleId));
    }

    public void deleteArticle(Long articleId) {
        log.info("Deleting Article, articleId = {}", articleId);

        URI uri = UriComponentsBuilder.fromHttpUrl(simpleBoardProperty.baseUrl() + SimpleBoardUrls.REQUEST_ARTICLES_URL + "/" + articleId)
                .build()
                .toUri();

        restTemplate.delete(uri);
    }
}
