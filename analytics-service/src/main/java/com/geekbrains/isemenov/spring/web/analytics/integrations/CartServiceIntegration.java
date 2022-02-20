package com.geekbrains.isemenov.spring.web.analytics.integrations;

import com.geekbrains.isemenov.spring.web.api.analytics.AnalyticsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class CartServiceIntegration {
    private final RestTemplate restTemplate;
    private final WebClient cartServiceWebClient;


    public AnalyticsDto getDailyProductsAnalytics() {
        return cartServiceWebClient
                .get()
                .uri("/api/v1/cart/analytics/day")
                .retrieve()
                .bodyToMono(AnalyticsDto.class)
                .block();

    }
}
