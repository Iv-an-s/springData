package com.geekbrains.isemenov.spring.web.core.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.geekbrains.isemenov.spring.web.core.entities.Order;

import java.util.List;

@Repository
public interface OrdersRepository extends JpaRepository<Order, Long> {
    @Query("select o from Order o where o.username = ?1")
    List<Order> findAllByUsername(String username);
}
