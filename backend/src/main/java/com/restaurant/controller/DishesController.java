package com.restaurant.controller;

import com.restaurant.entity.Dishes;
import com.restaurant.service.DishesService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
public class DishesController {

    private final DishesService dishesService;

    @Value("${app.upload-dir:uploads}")
    private String uploadDir;

    public DishesController(DishesService dishesService) {
        this.dishesService = dishesService;
    }

    @GetMapping("/getAllDishes")
    public List<Dishes> getAllDishes() {
        return dishesService.getAllDishes();
    }

    @PostMapping("/getDishesById")
    public Dishes getDishesById(@RequestBody Long id) {
        return dishesService.getDishesById(id);
    }

    @PostMapping("/editDishes")
    public void editDishes(@RequestBody Dishes dishes) {
        dishesService.editDishes(dishes);
    }

    @PostMapping("/removeDishes")
    public void removeDishes(@RequestBody Long id) {
        dishesService.removeDishes(id);
    }

    @PostMapping("/updateDishesState")
    public void updateDishesState(@RequestBody Dishes dishes) {
        dishesService.updateDishesState(dishes);
    }

    @PostMapping("/searchDishesName")
    public List<Dishes> searchDishesName(@RequestBody String name) {
        return dishesService.searchDishesName(stripJsonQuotes(name));
    }

    @PostMapping("/searchDishesType")
    public List<Dishes> searchDishesType(@RequestBody String type) {
        return dishesService.searchDishesType(stripJsonQuotes(type));
    }

    private String stripJsonQuotes(String s) {
        if (s != null && s.startsWith("\"") && s.endsWith("\"")) {
            return s.substring(1, s.length() - 1);
        }
        return s;
    }

    @PostMapping("/addDishes")
    public Dishes addDishes(
            @RequestParam("name") String name,
            @RequestParam("type") String type,
            @RequestParam("price") Double price,
            @RequestParam(value = "sales_volume", required = false) Integer salesVolume,
            @RequestParam(value = "state", required = false) Boolean state,
            @RequestParam(value = "file", required = false) MultipartFile file) throws IOException {

        Dishes dishes = new Dishes();
        dishes.setName(name);
        dishes.setType(type);
        dishes.setPrice(price);
        dishes.setSales_volume(salesVolume != null ? salesVolume : 0);
        dishes.setState(state != null ? state : true);

        if (file != null && !file.isEmpty()) {
            File dir = new File(uploadDir);
            if (!dir.exists()) dir.mkdirs();
            String filename = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            file.transferTo(new File(dir, filename));
            dishes.setImage(filename);
        }

        return dishesService.addDishes(dishes);
    }
}
