package com.guardjo.simpleboard.admin.controller;

import com.guardjo.simpleboard.admin.service.VisitCountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
@RequiredArgsConstructor
public class VisitCountAdvice {
    private final VisitCountService visitCountService;

    @ModelAttribute("visitCount")
    public long countVisitor() {
        return visitCountService.countVisitor();
    }
}
