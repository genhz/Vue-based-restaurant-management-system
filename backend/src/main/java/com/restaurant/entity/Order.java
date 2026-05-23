package com.restaurant.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    private String id;

    private Integer desk;
    private String date;
    private String notes;
    private Double total;
    private Boolean status;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "order_id")
    private List<OrderDish> dishesList = new ArrayList<>();

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public Integer getDesk() { return desk; }
    public void setDesk(Integer desk) { this.desk = desk; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public Double getTotal() { return total; }
    public void setTotal(Double total) { this.total = total; }

    public Boolean getStatus() { return status; }
    public void setStatus(Boolean status) { this.status = status; }

    public List<OrderDish> getDishesList() { return dishesList; }
    public void setDishesList(List<OrderDish> dishesList) { this.dishesList = dishesList; }
}
