package com.restaurant.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class DishMaterial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    private Long materialId;
    private String m_name;
    private Integer number;
    private Double price;
    private String unit;
    private Integer stock;
    private Integer safety_stock;
    private String supplier;
    private Integer batch;

    @JsonIgnore
    private Long dish_id;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getMaterialId() { return materialId; }
    public void setMaterialId(Long materialId) { this.materialId = materialId; }

    public String getM_name() { return m_name; }
    public void setM_name(String m_name) { this.m_name = m_name; }

    public Integer getNumber() { return number; }
    public void setNumber(Integer number) { this.number = number; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public String getUnit() { return unit; }
    public void setUnit(String unit) { this.unit = unit; }

    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }

    public Integer getSafety_stock() { return safety_stock; }
    public void setSafety_stock(Integer safety_stock) { this.safety_stock = safety_stock; }

    public String getSupplier() { return supplier; }
    public void setSupplier(String supplier) { this.supplier = supplier; }

    public Integer getBatch() { return batch; }
    public void setBatch(Integer batch) { this.batch = batch; }

    public Long getDish_id() { return dish_id; }
    public void setDish_id(Long dish_id) { this.dish_id = dish_id; }
}
