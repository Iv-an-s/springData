package com.geekbrains.isemenov.spring.web.analytics.converters;

import com.geekbrains.isemenov.spring.web.analytics.entityes.DayProduct;
import com.geekbrains.isemenov.spring.web.api.analytics.ProductAnalyticsDto;
import com.geekbrains.isemenov.spring.web.api.core.OrderItemDto;
import org.springframework.stereotype.Component;

@Component
public class DayProductConverter {
    public ProductAnalyticsDto entityToDto(DayProduct dayProduct){
        return new ProductAnalyticsDto(dayProduct.getProductId(), dayProduct.getTitle(), dayProduct.getQuantity());
    }
}
