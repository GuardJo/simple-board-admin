package com.guardjo.simpleboard.admin.service;

import com.guardjo.simpleboard.admin.controller.constant.UrlConstant;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VisitCountService {
    private final MeterRegistry meterRegistry;

    private final static List<String> SERVICE_URLS = List.of(
            UrlConstant.ARTICLE_MANAGEMENT_URL_PREFIX,
            UrlConstant.COMMENT_MANAGEMENT_URL_PREFIX,
            UrlConstant.MEMBER_MANAGEMENT_URL_PREFIX,
            UrlConstant.ADMIN_ACCOUNT_API_URL
    );

    public long countVisitor() {
        long totalCount = 0L;

        try {
            totalCount = meterRegistry.get("http.server.requests")
                    .timers()
                    .stream()
                    .filter(timer -> SERVICE_URLS.contains(timer.getId().getTag("uri")))
                    .mapToLong(Timer::count)
                    .sum();
        } catch (Exception e) {
            return 0L;
        }

        return totalCount;
    }
}
