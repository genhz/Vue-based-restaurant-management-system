package com.restaurant.service;

import com.restaurant.entity.DishMaterial;
import com.restaurant.entity.Dishes;
import com.restaurant.entity.Material;
import com.restaurant.repository.DishMaterialRepository;
import com.restaurant.repository.DishesRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DishesService {

    private final DishesRepository dishesRepository;
    private final DishMaterialRepository dishMaterialRepository;

    public DishesService(DishesRepository dishesRepository,
                         DishMaterialRepository dishMaterialRepository) {
        this.dishesRepository = dishesRepository;
        this.dishMaterialRepository = dishMaterialRepository;
    }

    public List<Dishes> getAllDishes() {
        return dishesRepository.findAll();
    }

    public Dishes getDishesById(Long id) {
        return dishesRepository.findById(id).orElse(null);
    }

    @Transactional
    public Dishes addDishes(Dishes dishes) {
        if (dishes.getMaterialList() != null) {
            for (DishMaterial dm : dishes.getMaterialList()) {
                dm.setDish_id(dishes.getId());
            }
        }
        return dishesRepository.save(dishes);
    }

    @Transactional
    public void editDishes(Dishes dishes) {
        Dishes existing = dishesRepository.findById(dishes.getId()).orElse(null);
        if (existing == null) return;

        existing.setName(dishes.getName());
        existing.setType(dishes.getType());
        existing.setPrice(dishes.getPrice());
        existing.setSales_volume(dishes.getSales_volume());
        if (dishes.getImage() != null) {
            existing.setImage(dishes.getImage());
        }

        // replace material list
        existing.getMaterialList().clear();
        if (dishes.getMaterialList() != null) {
            for (DishMaterial dm : dishes.getMaterialList()) {
                dm.setDish_id(existing.getId());
                existing.getMaterialList().add(dm);
            }
        }
        dishesRepository.save(existing);
    }

    @Transactional
    public void removeDishes(Long id) {
        dishesRepository.deleteById(id);
    }

    @Transactional
    public void updateDishesState(Dishes dishes) {
        Dishes existing = dishesRepository.findById(dishes.getId()).orElse(null);
        if (existing != null) {
            existing.setState(dishes.getState());
            dishesRepository.save(existing);
        }
    }

    public List<Dishes> searchDishesName(String name) {
        return dishesRepository.findByNameContaining(name);
    }

    public List<Dishes> searchDishesType(String type) {
        return dishesRepository.findByTypeContaining(type);
    }
}
