package com.guardjo.simpleboard.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RootController {
    /**
     * 루트 페이지 접근 시 특정 경로로 forwarding 하도록 지정
     * @return  forwarding 뷰 경로
     */
    @GetMapping
    public String root() {
        return "forwarding:management/article";
    }
}