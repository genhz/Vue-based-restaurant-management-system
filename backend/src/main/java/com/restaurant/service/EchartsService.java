package com.restaurant.service;

import com.restaurant.entity.*;
import com.restaurant.repository.*;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class EchartsService {

    private final OrderRepository orderRepository;
    private final MaterialRepository materialRepository;
    private final ProcurementRepository procurementRepository;
    private final SalaryRepository salaryRepository;
    private final AttendanceRepository attendanceRepository;
    private final PersonnelRepository personnelRepository;

    public EchartsService(OrderRepository orderRepository,
                          MaterialRepository materialRepository,
                          ProcurementRepository procurementRepository,
                          SalaryRepository salaryRepository,
                          AttendanceRepository attendanceRepository,
                          PersonnelRepository personnelRepository) {
        this.orderRepository = orderRepository;
        this.materialRepository = materialRepository;
        this.procurementRepository = procurementRepository;
        this.salaryRepository = salaryRepository;
        this.attendanceRepository = attendanceRepository;
        this.personnelRepository = personnelRepository;
    }

    public Map<String, Object> getWeekIncome() {
        LocalDate today = LocalDate.now();
        LocalDate monday = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate sunday = today.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));

        List<String> xdata = new ArrayList<>();
        List<Double> ydata = new ArrayList<>();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        for (LocalDate d = monday; !d.isAfter(sunday); d = d.plusDays(1)) {
            xdata.add(d.format(fmt));
            String dateStr = d.format(fmt);
            List<Order> orders = orderRepository.findByStatusTrueAndDateBetween(dateStr, dateStr);
            double income = orders.stream()
                    .mapToDouble(o -> o.getTotal() != null ? o.getTotal() : 0).sum();
            ydata.add(income);
        }

        Map<String, Object> result = new HashMap<>();
        result.put("xdata", xdata);
        result.put("ydata", ydata);
        return result;
    }

    public Map<String, Object> getWeekOrder() {
        LocalDate today = LocalDate.now();
        LocalDate monday = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate sunday = today.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));

        List<String> xdata = new ArrayList<>();
        List<Integer> ydata = new ArrayList<>();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        for (LocalDate d = monday; !d.isAfter(sunday); d = d.plusDays(1)) {
            xdata.add(d.format(fmt));
            String dateStr = d.format(fmt);
            List<Order> orders = orderRepository.findByStatusTrueAndDateBetween(dateStr, dateStr);
            ydata.add(orders.size());
        }

        Map<String, Object> result = new HashMap<>();
        result.put("xdata", xdata);
        result.put("ydata", ydata);
        return result;
    }

    public Map<String, Object> getInfor() {
        LocalDate today = LocalDate.now();
        String todayStr = today.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate monday = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate sunday = today.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));

        // day income & orders
        List<Order> dayOrders = orderRepository.findByStatusTrueAndDateBetween(todayStr, todayStr);
        double dayIncome = dayOrders.stream()
                .mapToDouble(o -> o.getTotal() != null ? o.getTotal() : 0).sum();
        int dayOrder = dayOrders.size();

        // week income & orders
        List<Order> weekOrders = orderRepository.findByStatusTrueAndDateBetween(
                monday.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                sunday.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        double weekIncome = weekOrders.stream()
                .mapToDouble(o -> o.getTotal() != null ? o.getTotal() : 0).sum();
        int weekOrder = weekOrders.size();

        Map<String, Object> result = new HashMap<>();
        result.put("dayIncome", dayIncome);
        result.put("dayOrder", dayOrder);
        result.put("weekIncome", weekIncome);
        result.put("weekOrder", weekOrder);
        return result;
    }

    public List<Map<String, Object>> getMaterialNamePidservice() {
        List<Material> materials = materialRepository.findAll();
        List<Map<String, Object>> result = new ArrayList<>();
        for (Material m : materials) {
            Map<String, Object> map = new HashMap<>();
            map.put("value", m.getStock() != null ? m.getStock() : 0);
            map.put("name", m.getM_name());
            result.add(map);
        }
        return result;
    }

    public List<Map<String, Object>> getMaterialSuppliedPidservice() {
        List<Material> materials = materialRepository.findAll();
        Map<String, Integer> supplierCount = new HashMap<>();
        for (Material m : materials) {
            String supplier = m.getSupplier() != null ? m.getSupplier() : "未知";
            supplierCount.put(supplier, supplierCount.getOrDefault(supplier, 0) + 1);
        }
        List<Map<String, Object>> result = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : supplierCount.entrySet()) {
            Map<String, Object> map = new HashMap<>();
            map.put("value", entry.getValue());
            map.put("name", entry.getKey());
            result.add(map);
        }
        return result;
    }

    public Map<String, Object> getMonthprofit() {
        return getMonthData(true);
    }

    public Map<String, Object> getMonthpay() {
        return getMonthData(false);
    }

    private Map<String, Object> getMonthData(boolean isProfit) {
        LocalDate now = LocalDate.now();
        List<String> xdata = new ArrayList<>();
        List<Double> ydata = new ArrayList<>();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM");

        for (int i = 5; i >= 0; i--) {
            LocalDate month = now.minusMonths(i);
            String monthStr = month.format(fmt);
            xdata.add(monthStr);

            if (isProfit) {
                String start = month.withDayOfMonth(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                String end = month.withDayOfMonth(month.lengthOfMonth())
                        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                List<Order> orders = orderRepository.findByStatusTrueAndDateBetween(start, end);
                double total = orders.stream()
                        .mapToDouble(o -> o.getTotal() != null ? o.getTotal() : 0).sum();
                ydata.add(total);
            } else {
                String start = month.withDayOfMonth(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                String end = month.withDayOfMonth(month.lengthOfMonth())
                        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                List<Procurement> procs = procurementRepository.findByP_dateBetween(start, end);
                double total = procs.stream()
                        .mapToDouble(p -> p.getP_total() != null ? p.getP_total() : 0).sum();
                ydata.add(total);
            }
        }

        Map<String, Object> result = new HashMap<>();
        result.put("xdata", xdata);
        result.put("ydata", ydata);
        return result;
    }

    public Map<String, Object> getmonthall() {
        LocalDate now = LocalDate.now();
        String monthStr = now.format(DateTimeFormatter.ofPattern("yyyy-MM"));
        String start = now.withDayOfMonth(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String end = now.withDayOfMonth(now.lengthOfMonth())
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        // month income (profit)
        List<Order> monthOrders = orderRepository.findByStatusTrueAndDateBetween(start, end);
        double monthIncome = monthOrders.stream()
                .mapToDouble(o -> o.getTotal() != null ? o.getTotal() : 0).sum();
        int monthOrderCount = monthOrders.size();

        // month pay (procurement)
        List<Procurement> monthProcs = procurementRepository.findByP_dateBetween(start, end);
        double monthProcurement = monthProcs.stream()
                .mapToDouble(p -> p.getP_total() != null ? p.getP_total() : 0).sum();

        // month salary
        List<Salary> monthSalaries = salaryRepository.findByMonth(monthStr);
        double monthSalary = monthSalaries.stream()
                .mapToDouble(s -> s.getSalary() != null ? s.getSalary() : 0).sum();

        double monthPay = monthProcurement + monthSalary;
        double monthProfit = monthIncome - monthPay;

        Map<String, Object> result = new HashMap<>();
        result.put("monthinput", monthIncome);
        result.put("monthpay", monthPay);
        result.put("monthprofit", monthProfit);
        result.put("monthorders", monthOrderCount);
        result.put("monthsalary", monthSalary);
        return result;
    }
}
