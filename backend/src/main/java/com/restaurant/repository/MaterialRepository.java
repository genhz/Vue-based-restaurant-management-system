package com.restaurant.repository;

import com.restaurant.entity.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MaterialRepository extends JpaRepository<Material, Long> {

    @Query("SELECT m FROM Material m WHERE m.m_name LIKE ?1")
    List<Material> findByM_nameContaining(String name);

    List<Material> findBySupplierContaining(String supplier);
}
