package com.restaurant.repository;

import com.restaurant.entity.Personnel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonnelRepository extends JpaRepository<Personnel, Long> {
    Personnel findByUsername(String username);

    @Query("SELECT p FROM Personnel p WHERE p.p_name LIKE ?1")
    List<Personnel> findByP_nameContaining(String name);

    @Query("SELECT p FROM Personnel p WHERE p.p_type LIKE ?1")
    List<Personnel> findByP_typeContaining(String type);
}
