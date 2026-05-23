package com.restaurant.controller;

import com.restaurant.entity.*;
import com.restaurant.service.PayService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PayController {

    private final PayService payService;

    public PayController(PayService payService) {
        this.payService = payService;
    }

    @GetMapping("/getAllPay")
    public List<Pay> getAllPay() {
        return payService.getAllPay();
    }

    @GetMapping("/getAllProfit")
    public List<Profit> getAllProfit() {
        return payService.getAllProfit();
    }

    @PostMapping("/getProfitId")
    public List<Order> getProfitId(@RequestBody Long id) {
        return payService.getProfitId(id);
    }

    @PostMapping("/getPayIdprocure")
    public List<Procurement> getPayIdprocure(@RequestBody Long id) {
        return payService.getPayIdprocure(id);
    }

    @PostMapping("/getPayIdsalary")
    public List<Salary> getPayIdsalary(@RequestBody Long id) {
        return payService.getPayIdsalary(id);
    }
}
