package com.guardjo.simpleboard.admin.service;

import com.guardjo.simpleboard.admin.model.ArticleDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ArticleManagementService {
    public List<ArticleDto> findArticles() {
        log.info("Finding Articles");

        return List.of();
    }

    public ArticleDto findArticle(Long articleId) {
        log.info("Finding Article, articleId = {}", articleId);

        return null;
    }

    public void deleteArticle(Long articleId) {
        log.info("Deleting Article, articleId = {}", articleId);
    }
}
