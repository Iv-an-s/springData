package com.geekbrains.isemenov.spring.web.endpoints;

import com.geekbrains.isemenov.spring.web.products.*;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import com.geekbrains.isemenov.spring.web.entities.Product;
import com.geekbrains.isemenov.spring.web.exceptions.ResourceNotFoundException;
import com.geekbrains.isemenov.spring.web.services.ProductsService;

import java.util.function.Consumer;

@Endpoint
@RequiredArgsConstructor
public class ProductEndpoint {
    private static final String NAMESPACE_URI = "http://www.isemenov.ru/springData/products";
    private final ProductsService productsService;

    /*
        Пример запроса: POST http://localhost:8080/ws

        <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
          xmlns:f="http://www.flamexander.com/spring/ws/groups">
            <soapenv:Header/>
            <soapenv:Body>
                <f:getGroupByTitleRequest>
                    <f:title>ABC-123</f:title>
                </f:getGroupByTitleRequest>
            </soapenv:Body>
        </soapenv:Envelope>
     */

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getProductByIdRequest")
    @ResponsePayload
    @Transactional
    public GetProductByIdResponse getProductById(@RequestPayload GetProductByIdRequest request) {
        GetProductByIdResponse response = new GetProductByIdResponse();
        response.setProduct(new ProductSoap(productsService.findById(request.getId()).orElseThrow(()-> new ResourceNotFoundException("Нет продукта по такому id"))));
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllProductsRequest")
    @ResponsePayload
    @Transactional
    public GetAllProductsResponse getAllProducts(@RequestPayload GetAllProductsRequest request) {
        GetAllProductsResponse response = new GetAllProductsResponse();
        productsService.findAllBy().forEach(new Consumer<Product>() {
            @Override
            public void accept(Product product) {
                response.getProducts().add(new ProductSoap(product));
            }
        });
        return response;
    }
}
