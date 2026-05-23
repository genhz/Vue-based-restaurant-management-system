package com.restaurant.controller;

import com.restaurant.entity.Order;
import com.restaurant.entity.OrderDish;
import com.restaurant.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/addOrder")
    public Order addOrder(@RequestBody Order order) {
        return orderService.addOrder(order);
    }

    @GetMapping("/getAllOrder")
    public List<Order> getAllOrder() {
        return orderService.getAllOrder();
    }

    @GetMapping("/getfinOrder")
    public List<Order> getfinOrder() {
        return orderService.getfinOrder();
    }

    @GetMapping("/getunOrder")
    public List<Order> getunOrder() {
        return orderService.getunOrder();
    }

    @PostMapping("/deleteOrder")
    public void deleteOrder(@RequestBody String id) {
        orderService.deleteOrder(stripJsonQuotes(id));
    }

    @PostMapping("/updateOrderStatus")
    public void updateOrderStatus(@RequestBody Order order) {
        orderService.updateOrderStatus(order);
    }

    @PostMapping("/getOrderById")
    public Order getOrderById(@RequestBody String id) {
        return orderService.getOrderById(stripJsonQuotes(id));
    }

    @PostMapping("/editOrder")
    public void editOrder(@RequestBody Order order) {
        orderService.editOrder(order);
    }

    @PostMapping("/addMenuOrder")
    public Order addMenuOrder(@RequestBody Order order) {
        return orderService.addMenuOrder(order);
    }

    @PostMapping("/getOrderByDesk")
    public List<OrderDish> getOrderByDesk(@RequestBody Integer desk) {
        return orderService.getOrderByDesk(desk);
    }

    private String stripJsonQuotes(String s) {
        if (s != null && s.startsWith("\"") && s.endsWith("\"")) {
            return s.substring(1, s.length() - 1);
        }
        return s;
    }
}
