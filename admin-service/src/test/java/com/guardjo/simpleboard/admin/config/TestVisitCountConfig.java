package com.guardjo.simpleboard.admin.config;

import com.guardjo.simpleboard.admin.service.VisitCountService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.event.annotation.BeforeTestMethod;

import static org.mockito.BDDMockito.given;

@TestConfiguration
public class TestVisitCountConfig {
    @MockBean
    private VisitCountService visitCountService;

    @BeforeTestMethod
    public void init() {
        given(visitCountService.countVisitor()).willReturn(999L);
    }
}
