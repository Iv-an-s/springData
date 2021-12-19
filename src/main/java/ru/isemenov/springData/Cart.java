package ru.isemenov.springData;

import lombok.Data;
import org.springframework.stereotype.Component;
import ru.isemenov.springData.entities.Product;

import java.util.ArrayList;
import java.util.List;

@Component
@Data
public class Cart {
    List<Product> productList;

    public Cart() {
        this.productList = new ArrayList<>();
    }

    public void addProduct(Product product) {
        productList.add(product);
    }

    public void removeProduct(Product product) {
        productList.remove(product);
    }

    public void clear() {
        productList.clear();
    }
}
