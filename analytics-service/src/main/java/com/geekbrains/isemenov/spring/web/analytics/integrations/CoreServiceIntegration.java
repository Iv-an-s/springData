package com.geekbrains.isemenov.spring.web.analytics.integrations;

import com.geekbrains.isemenov.spring.web.api.analytics.AnalyticsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class CoreServiceIntegration {
    private final WebClient coreServiceWebClient;

    public AnalyticsDto getDailyProductsAnalytics() {
        return coreServiceWebClient
                .get()
                .uri("/api/v1/orders/analytics")
                .retrieve()
                .bodyToMono(AnalyticsDto.class)
                .block();
    }
}
