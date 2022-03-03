package com.geekbrains.isemenov.spring.web.cart.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConstructorBinding
@ConfigurationProperties(prefix = "integrations.core-service")
@Data
public class CoreServiceIntegrationProperties {
    private String url;
    @Autowired
    private TimeoutsProperties timeoutsProperties;
}
