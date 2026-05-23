package com.restaurant.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Dishes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String type;
    private String image;
    private Double price;
    private Integer sales_volume;
    private Boolean state;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "dish_id")
    private List<DishMaterial> materialList = new ArrayList<>();

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public Integer getSales_volume() { return sales_volume; }
    public void setSales_volume(Integer sales_volume) { this.sales_volume = sales_volume; }

    public Boolean getState() { return state; }
    public void setState(Boolean state) { this.state = state; }

    public List<DishMaterial> getMaterialList() { return materialList; }
    public void setMaterialList(List<DishMaterial> materialList) { this.materialList = materialList; }
}
