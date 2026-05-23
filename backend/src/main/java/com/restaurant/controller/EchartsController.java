package com.restaurant.controller;

import com.restaurant.service.EchartsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class EchartsController {

    private final EchartsService echartsService;

    public EchartsController(EchartsService echartsService) {
        this.echartsService = echartsService;
    }

    @GetMapping("/getWeekIncome")
    public Map<String, Object> getWeekIncome() {
        return echartsService.getWeekIncome();
    }

    @GetMapping("/getWeekOrder")
    public Map<String, Object> getWeekOrder() {
        return echartsService.getWeekOrder();
    }

    @GetMapping("/getInfor")
    public Map<String, Object> getInfor() {
        return echartsService.getInfor();
    }

    @GetMapping("/getMateialNamePidservice")
    public List<Map<String, Object>> getMaterialNamePidservice() {
        return echartsService.getMaterialNamePidservice();
    }

    @GetMapping("/getMateialSuppliedPidservice")
    public List<Map<String, Object>> getMaterialSuppliedPidservice() {
        return echartsService.getMaterialSuppliedPidservice();
    }

    @GetMapping("/getMonthprofit")
    public Map<String, Object> getMonthprofit() {
        return echartsService.getMonthprofit();
    }

    @GetMapping("/getMonthpay")
    public Map<String, Object> getMonthpay() {
        return echartsService.getMonthpay();
    }

    @GetMapping("/getmonthall")
    public Map<String, Object> getmonthall() {
        return echartsService.getmonthall();
    }
}
