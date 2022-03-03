package com.geekbrains.isemenov.spring.web.cart.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConstructorBinding
@ConfigurationProperties(prefix = "integrations.core-service.timeouts")
@Data
public class TimeoutsProperties {
    private Integer connectTimeout;
    private Integer readTimeout;
    private Integer writeTimeout;
}
