package com.restaurant.entity;

import javax.persistence.*;

@Entity
public class Pay {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String month;
    private Double pay_total;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getMonth() { return month; }
    public void setMonth(String month) { this.month = month; }

    public Double getPay_total() { return pay_total; }
    public void setPay_total(Double pay_total) { this.pay_total = pay_total; }
}
