package com.restaurant.entity;

import javax.persistence.*;

@Entity
public class Personnel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String p_name;
    private String username;
    private String password;
    private String phone;
    private String p_type;
    private String role;
    private Double b_salary;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getP_name() { return p_name; }
    public void setP_name(String p_name) { this.p_name = p_name; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getP_type() { return p_type; }
    public void setP_type(String p_type) { this.p_type = p_type; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public Double getB_salary() { return b_salary; }
    public void setB_salary(Double b_salary) { this.b_salary = b_salary; }
}
