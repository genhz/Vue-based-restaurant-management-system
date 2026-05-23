package com.restaurant.repository;

import com.restaurant.entity.Dishes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DishesRepository extends JpaRepository<Dishes, Long> {
    List<Dishes> findByNameContaining(String name);
    List<Dishes> findByTypeContaining(String type);
    List<Dishes> findByStateTrue();
}
