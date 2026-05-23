package com.restaurant.service;

import com.restaurant.entity.*;
import com.restaurant.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PayService {

    private final PayRepository payRepository;
    private final ProfitRepository profitRepository;
    private final OrderRepository orderRepository;
    private final ProcurementRepository procurementRepository;
    private final SalaryRepository salaryRepository;

    public PayService(PayRepository payRepository, ProfitRepository profitRepository,
                      OrderRepository orderRepository,
                      ProcurementRepository procurementRepository,
                      SalaryRepository salaryRepository) {
        this.payRepository = payRepository;
        this.profitRepository = profitRepository;
        this.orderRepository = orderRepository;
        this.procurementRepository = procurementRepository;
        this.salaryRepository = salaryRepository;
    }

    public List<Pay> getAllPay() {
        return payRepository.findAll();
    }

    public List<Profit> getAllProfit() {
        return profitRepository.findAll();
    }

    public List<Order> getProfitId(Long id) {
        Profit profit = profitRepository.findById(id).orElse(null);
        if (profit == null) return null;
        // return orders for that month
        return orderRepository.findByStatusTrueAndDateBetween(
                profit.getMonth() + "-01", profit.getMonth() + "-31");
    }

    public List<Procurement> getPayIdprocure(Long id) {
        Pay pay = payRepository.findById(id).orElse(null);
        if (pay == null) return null;
        return procurementRepository.findByP_dateBetween(
                pay.getMonth() + "-01", pay.getMonth() + "-31");
    }

    public List<Salary> getPayIdsalary(Long id) {
        Pay pay = payRepository.findById(id).orElse(null);
        if (pay == null) return null;
        return salaryRepository.findByMonth(pay.getMonth());
    }
}
