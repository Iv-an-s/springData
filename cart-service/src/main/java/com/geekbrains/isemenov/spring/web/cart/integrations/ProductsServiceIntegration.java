package com.geekbrains.isemenov.spring.web.cart.integrations;

import com.geekbrains.isemenov.spring.web.api.core.ProductDto;
import com.geekbrains.isemenov.spring.web.api.exceptions.NoConnectionWithServiceException;
import com.geekbrains.isemenov.spring.web.api.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ProductsServiceIntegration {
    private final WebClient productServiceWebClient;

    @Value("${integrations.core-service.url}")
    private String productServiceUrl;

    public Optional<ProductDto> findById(Long id) {
        ProductDto productDto = productServiceWebClient
                .get()
                .uri(productServiceUrl + "/api/v1/products/" + id)
                .retrieve()
                .onStatus(HttpStatus::is5xxServerError, clientResponse -> Mono.error(new NoConnectionWithServiceException("Product server is not availible")))
                .onStatus(HttpStatus::is4xxClientError, clientResponse -> Mono.error(new ResourceNotFoundException("Product not found, id: " + id)))
                .bodyToMono(ProductDto.class)
                .block();
        return Optional.ofNullable(productDto);
    }

//    private final RestTemplate restTemplate;
//
//    @Value("${integrations.core-service.url}")
//    private String productServiceUrl;
//
//    public Optional<ProductDto> findById(Long id){
//        ProductDto productDto = restTemplate.getForObject(productServiceUrl + "/api/v1/products/" + id, ProductDto.class);
//        return Optional.ofNullable(productDto);
//    }
}
