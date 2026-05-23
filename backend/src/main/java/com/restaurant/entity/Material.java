package com.restaurant.entity;

import javax.persistence.*;

@Entity
public class Material {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String m_name;
    private String supplier;
    private Double price;
    private String unit;
    private Integer batch;
    private Integer safety_stock;
    private Integer stock;

    @Transient
    private Integer number;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getM_name() { return m_name; }
    public void setM_name(String m_name) { this.m_name = m_name; }

    public String getSupplier() { return supplier; }
    public void setSupplier(String supplier) { this.supplier = supplier; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public String getUnit() { return unit; }
    public void setUnit(String unit) { this.unit = unit; }

    public Integer getBatch() { return batch; }
    public void setBatch(Integer batch) { this.batch = batch; }

    public Integer getSafety_stock() { return safety_stock; }
    public void setSafety_stock(Integer safety_stock) { this.safety_stock = safety_stock; }

    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }

    public Integer getNumber() { return number; }
    public void setNumber(Integer number) { this.number = number; }
}
