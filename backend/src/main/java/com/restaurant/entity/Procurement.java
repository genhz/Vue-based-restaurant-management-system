package com.restaurant.entity;

import javax.persistence.*;

@Entity
public class Procurement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String p_date;
    private Double p_total;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getP_date() { return p_date; }
    public void setP_date(String p_date) { this.p_date = p_date; }

    public Double getP_total() { return p_total; }
    public void setP_total(Double p_total) { this.p_total = p_total; }
}
