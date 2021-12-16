package ru.isemenov.springData.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.isemenov.springData.entities.Product;

import java.util.List;

@Repository
public interface ProductsRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    List<Product> findAllByPriceBetween(Integer min, Integer max);

    @Query("select p from Product p where p.price > :min")
    List<Product> findProductsWithPriceMoreThanMin(Integer min);

    @Query("select p from Product p where p.price < :max")
    List<Product> findProductsWithPriceLessThanMax(Integer max);
}
