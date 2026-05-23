package com.restaurant.repository;

import com.restaurant.entity.DishMaterial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DishMaterialRepository extends JpaRepository<DishMaterial, Long> {

    @Query("SELECT dm FROM DishMaterial dm WHERE dm.dish_id = ?1")
    List<DishMaterial> findByDish_id(Long dishId);

    @Modifying
    @Query("DELETE FROM DishMaterial dm WHERE dm.dish_id = ?1")
    void deleteByDish_id(Long dishId);
}
