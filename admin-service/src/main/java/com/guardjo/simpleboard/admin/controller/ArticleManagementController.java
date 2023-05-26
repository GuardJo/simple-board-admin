package com.guardjo.simpleboard.admin.controller;

import com.guardjo.simpleboard.admin.controller.constant.UrlConstant;
import com.guardjo.simpleboard.admin.model.ArticleDto;
import com.guardjo.simpleboard.admin.service.ArticleManagementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(UrlConstant.ARTICLE_MANAGEMENT_URL_PREFIX)
@Controller
@Slf4j
@RequiredArgsConstructor
public class ArticleManagementController {
    private final ArticleManagementService articleManagementService;

    @GetMapping
    public String getDefaultViewPage(Model model) {
        log.info("Request Default Article Management Page");

        List<ArticleDto> articleDtoList = articleManagementService.findArticles();

        model.addAttribute("articles", articleDtoList);

        return "management/article";
    }

    @ResponseBody
    @GetMapping("/{articleId}")
    public ArticleDto getArticle(@PathVariable long articleId) {
        log.info("Request Get Article, articleId = {}", articleId);

        return articleManagementService.findArticle(articleId);
    }

    @DeleteMapping("/{articleId}")
    public String deleteArticle(@PathVariable long articleId) {
        log.info("Request Delete Article, articleId = {}", articleId);

        articleManagementService.deleteArticle(articleId);

        return "redirect:" + UrlConstant.ARTICLE_MANAGEMENT_URL_PREFIX;
    }
}
