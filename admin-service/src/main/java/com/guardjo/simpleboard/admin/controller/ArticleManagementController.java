package com.guardjo.simpleboard.admin.controller;

import com.guardjo.simpleboard.admin.controller.constant.UrlConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(UrlConstant.ARTICLE_MANAGEMENT_URL_PREFIX)
@Controller
@Slf4j
public class ArticleManagementController {
    @GetMapping
    public String getDefaultViewPage() {
        log.info("[Test] Request Default Article Management Page");
        return "management/article";
    }
}
