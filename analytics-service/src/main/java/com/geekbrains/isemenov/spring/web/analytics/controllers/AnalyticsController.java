package com.geekbrains.isemenov.spring.web.analytics.controllers;

import com.geekbrains.isemenov.spring.web.api.analytics.ProductAnalyticsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/v1/productsAnalytics")
@RequiredArgsConstructor
public class AnalyticsController {

    @GetMapping("/topOfMonth")
    public List<ProductAnalyticsDto> getTopMonthProducts(){
        List<ProductAnalyticsDto> mockData = new ArrayList<>();
        mockData.add(new ProductAnalyticsDto(1L, "Milk", 50));
        mockData.add(new ProductAnalyticsDto(2L, "Bread", 40));
        mockData.add(new ProductAnalyticsDto(2L, "Fish", 30));
        mockData.add(new ProductAnalyticsDto(2L, "Cheese", 20));
        mockData.add(new ProductAnalyticsDto(2L, "Butter", 10));
        return mockData;
    }

    @GetMapping("/topOfDay")
    public List<ProductAnalyticsDto> getTopDayProducts(){
        List<ProductAnalyticsDto> mockData = new ArrayList<>();
        mockData.add(new ProductAnalyticsDto(1L, "Milk", 50));
        mockData.add(new ProductAnalyticsDto(2L, "Bread", 40));
        mockData.add(new ProductAnalyticsDto(2L, "Fish", 30));
        mockData.add(new ProductAnalyticsDto(2L, "Cheese", 20));
        mockData.add(new ProductAnalyticsDto(2L, "Butter", 10));
        return mockData;
    }
}
