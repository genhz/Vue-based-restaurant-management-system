package com.restaurant.service;

import com.restaurant.entity.Order;
import com.restaurant.entity.OrderDish;
import com.restaurant.repository.OrderDishRepository;
import com.restaurant.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderDishRepository orderDishRepository;

    public OrderService(OrderRepository orderRepository,
                        OrderDishRepository orderDishRepository) {
        this.orderRepository = orderRepository;
        this.orderDishRepository = orderDishRepository;
    }

    @Transactional
    public Order addOrder(Order order) {
        String id = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        order.setId(id);
        order.setDate(date);
        order.setStatus(false);
        if (order.getDishesList() != null) {
            for (OrderDish od : order.getDishesList()) {
                od.setOrder_id(order.getId());
            }
        }
        return orderRepository.save(order);
    }

    public List<Order> getAllOrder() {
        return orderRepository.findAll();
    }

    public List<Order> getfinOrder() {
        return orderRepository.findByStatusTrue();
    }

    public List<Order> getunOrder() {
        return orderRepository.findByStatusFalse();
    }

    @Transactional
    public void deleteOrder(String id) {
        orderRepository.deleteById(id);
    }

    @Transactional
    public void updateOrderStatus(Order order) {
        Order existing = orderRepository.findById(order.getId()).orElse(null);
        if (existing != null) {
            existing.setStatus(order.getStatus());
            orderRepository.save(existing);
        }
    }

    public Order getOrderById(String id) {
        return orderRepository.findById(id).orElse(null);
    }

    @Transactional
    public void editOrder(Order order) {
        Order existing = orderRepository.findById(order.getId()).orElse(null);
        if (existing == null) return;

        existing.setDesk(order.getDesk());
        existing.setDate(order.getDate());
        existing.setNotes(order.getNotes());
        existing.setTotal(order.getTotal());

        existing.getDishesList().clear();
        if (order.getDishesList() != null) {
            for (OrderDish od : order.getDishesList()) {
                od.setOrder_id(existing.getId());
                existing.getDishesList().add(od);
            }
        }
        orderRepository.save(existing);
    }

    @Transactional
    public Order addMenuOrder(Order order) {
        return addOrder(order);
    }

    public List<OrderDish> getOrderByDesk(Integer desk) {
        List<Order> orders = orderRepository.findByStatusFalseAndDesk(desk);
        if (orders != null && !orders.isEmpty()) {
            return orders.get(0).getDishesList();
        }
        return null;
    }
}
