package com.restaurant.entity;

import javax.persistence.*;

@Entity
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long personnelId;
    private String date;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getPersonnelId() { return personnelId; }
    public void setPersonnelId(Long personnelId) { this.personnelId = personnelId; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
}
