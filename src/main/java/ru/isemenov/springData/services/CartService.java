package ru.isemenov.springData.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.isemenov.springData.Cart;
import ru.isemenov.springData.converters.ProductConverter;
import ru.isemenov.springData.dto.ProductDto;
import ru.isemenov.springData.entities.Product;
import ru.isemenov.springData.exceptions.ResourceNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {
    private final Cart cart;
    private final ProductsService productsService;
    private final ProductConverter productConverter;


    public List<ProductDto> showCart() {
        List<Product> productList = cart.getProductList();
        List<ProductDto> productDtoList = new ArrayList<>();
        for (Product product : productList) {
            productDtoList.add(productConverter.entityToDto(product));
        }
        return productDtoList;
        //return (List<ProductDto>) cart.getProductList().stream().map(p -> productConverter.entityToDto(p));
    }

    public void addProduct(Long productId) {
        Optional<Product> optionalProduct = productsService.findById(productId);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            cart.addProduct(product);
        } else {
            throw new ResourceNotFoundException("Product not found" + productId);
        }
    }

    public void removeProduct(Long productId) {
        List<Product> productList = cart.getProductList();
        for (Product product : productList) {
            if (product.getId().equals(productId)) {
                cart.removeProduct(product);
            }
        }
    }

    public void clear() {
        cart.clear();
    }
}
