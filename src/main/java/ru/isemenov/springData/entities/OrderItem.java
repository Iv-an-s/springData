package ru.isemenov.springData.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "order_entity")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "pricePerProduct")
    private int pricePerProduct;

    @Column(name = "price")
    private int price;

    public OrderItem(Long productId, Long orderId, int quantity, int pricePerProduct, int price) {
        this.productId = productId;
        this.orderId = orderId;
        this.quantity = quantity;
        this.pricePerProduct = pricePerProduct;
        this.price = price;
    }
}

