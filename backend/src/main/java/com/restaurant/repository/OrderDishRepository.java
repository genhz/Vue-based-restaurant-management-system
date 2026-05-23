package com.restaurant.repository;

import com.restaurant.entity.OrderDish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDishRepository extends JpaRepository<OrderDish, Long> {

    @Query("SELECT od FROM OrderDish od WHERE od.order_id = ?1")
    List<OrderDish> findByOrder_id(String orderId);

    @Modifying
    @Query("DELETE FROM OrderDish od WHERE od.order_id = ?1")
    void deleteByOrder_id(String orderId);
}
