package com.geekbrains.isemenov.spring.web.analytics.repository;

import com.geekbrains.isemenov.spring.web.analytics.entityes.DayProduct;
import com.geekbrains.isemenov.spring.web.analytics.entityes.MonthProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnalyticsRepository extends JpaRepository<DayProduct, Long>{
    @Query(value = "select id, product_id, title, quantity, created_at, updated_at from day_products group by product_id order by quantity desc where created_at > (current_timestamp- 24*60*60*1000) limit 5", nativeQuery = true)
//    @Query(value = "select * from day_products group by product_id order by quantity desc limit 5", nativeQuery = true)
    List<DayProduct> findMostPopularDailyProducts();

    @Query(value = "select id, product_id, title, quantity, created_at, updated_at from month_products group by product_id order by quantity desc where created_at > (current_timestamp- 30*24*60*60*1000) limit 5", nativeQuery = true)
    List<MonthProduct> findMostPopularMonthProducts();

}
