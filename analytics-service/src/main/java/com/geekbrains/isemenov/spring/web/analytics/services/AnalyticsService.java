package com.geekbrains.isemenov.spring.web.analytics.services;

import com.geekbrains.isemenov.spring.web.analytics.converters.DayProductConverter;
import com.geekbrains.isemenov.spring.web.analytics.integrations.CartServiceIntegration;
import com.geekbrains.isemenov.spring.web.analytics.repository.AnalyticsRepository;
import com.geekbrains.isemenov.spring.web.api.analytics.AnalyticsDto;
import com.geekbrains.isemenov.spring.web.api.analytics.ProductAnalyticsDto;
import com.geekbrains.isemenov.spring.web.api.carts.CartItemDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
@Slf4j
public class AnalyticsService {
    private final CartServiceIntegration cartServiceIntegration;
    private final AnalyticsRepository analyticsRepository;
    private final DayProductConverter dayProductConverter;


    public List<ProductAnalyticsDto> getTopDayProducts() {
        AnalyticsDto dailyProducts = cartServiceIntegration.getDailyProductsAnalytics();
        List <CartItemDto> cartItemDtoList = dailyProducts.getCartItemDtoList();
        List<ProductAnalyticsDto> dayProductList = new ArrayList<>();
        for (int i = 0; i < cartItemDtoList.size(); i++) {
//          log.debug("product #" + i + ": " + cartItemDtoList.get(0).getProductTitle());
            dayProductList.add(new ProductAnalyticsDto((long)i, cartItemDtoList.get(i).getProductTitle(), cartItemDtoList.get(i).getQuantity()));
        }
        return dayProductList;
    }

    public List<ProductAnalyticsDto> getTopMonthProducts() {
        return null;
    }
}
