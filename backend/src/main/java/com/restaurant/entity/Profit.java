package com.restaurant.entity;

import javax.persistence.*;

@Entity
public class Profit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String month;
    private Double pr_price;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getMonth() { return month; }
    public void setMonth(String month) { this.month = month; }

    public Double getPr_price() { return pr_price; }
    public void setPr_price(Double pr_price) { this.pr_price = pr_price; }
}
