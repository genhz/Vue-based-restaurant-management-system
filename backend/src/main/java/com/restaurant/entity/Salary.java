package com.restaurant.entity;

import javax.persistence.*;

@Entity
public class Salary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long per_id;
    private String per_name;
    private String month;
    private Integer day;
    private Double salary;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getPer_id() { return per_id; }
    public void setPer_id(Long per_id) { this.per_id = per_id; }

    public String getPer_name() { return per_name; }
    public void setPer_name(String per_name) { this.per_name = per_name; }

    public String getMonth() { return month; }
    public void setMonth(String month) { this.month = month; }

    public Integer getDay() { return day; }
    public void setDay(Integer day) { this.day = day; }

    public Double getSalary() { return salary; }
    public void setSalary(Double salary) { this.salary = salary; }
}
