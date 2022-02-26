package com.geekbrains.isemenov.spring.web.analytics.controllers;

import com.geekbrains.isemenov.spring.web.analytics.services.AnalyticsService;
import com.geekbrains.isemenov.spring.web.api.analytics.ProductAnalyticsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/productsAnalytics")
@RequiredArgsConstructor
public class AnalyticsController {
    private final AnalyticsService analyticsService;

//    @GetMapping("/topOfMonth")
//    public List<ProductAnalyticsDto> getTopMonthProducts(){
//        List<ProductAnalyticsDto> mockData = new ArrayList<>();
//        mockData.add(new ProductAnalyticsDto(1L, "Milk", 50));
//        mockData.add(new ProductAnalyticsDto(2L, "Bread", 40));
//        mockData.add(new ProductAnalyticsDto(3L, "Fish", 30));
//        mockData.add(new ProductAnalyticsDto(4L, "Cheese", 20));
//        mockData.add(new ProductAnalyticsDto(5L, "Butter", 10));
//        return mockData;
//    }

    @GetMapping("/topOfMonth")
    public List<ProductAnalyticsDto> getTopMonthProducts() {
        return analyticsService.getTopMonthProducts();
    }

    @GetMapping("/topOfDay")
    public List<ProductAnalyticsDto> getTopDayProducts() {
        return analyticsService.getTopDayProducts();
    }
}
