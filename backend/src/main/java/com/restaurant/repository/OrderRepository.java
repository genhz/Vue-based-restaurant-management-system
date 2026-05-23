package com.restaurant.repository;

import com.restaurant.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {
    List<Order> findByStatusTrue();
    List<Order> findByStatusFalse();
    List<Order> findByStatusFalseAndDesk(Integer desk);
    List<Order> findByDateBetween(String start, String end);
    List<Order> findByStatusTrueAndDateBetween(String start, String end);
}
