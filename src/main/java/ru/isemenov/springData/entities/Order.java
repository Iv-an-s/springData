package ru.isemenov.springData.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "total_price")
    private int price;

    @Column(name = "address")
    private String address;

    @Column(name = "phone")
    private String phone;

    public Order(Long userId, int price, String address, String phone) {
        this.userId = userId;
        this.price = price;
        this.address = address;
        this.phone = phone;
    }
}
