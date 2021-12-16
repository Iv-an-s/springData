package ru.isemenov.springData.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.isemenov.springData.dto.ProductDto;

import javax.persistence.*;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "price")
    private Integer price;


    public Product(ProductDto productDto) {
        this.id = productDto.getId();
        this.title = productDto.getTitle();
        this.price = productDto.getPrice();
    }
}
