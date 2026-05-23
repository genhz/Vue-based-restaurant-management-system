package com.restaurant.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class ProcurementDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    private Long procurement_id;

    private Integer number;
    private Double price;

    @ManyToOne
    @JoinColumn(name = "material_id")
    private Material material;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getProcurement_id() { return procurement_id; }
    public void setProcurement_id(Long procurement_id) { this.procurement_id = procurement_id; }

    public Integer getNumber() { return number; }
    public void setNumber(Integer number) { this.number = number; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public Material getMaterial() { return material; }
    public void setMaterial(Material material) { this.material = material; }
}
