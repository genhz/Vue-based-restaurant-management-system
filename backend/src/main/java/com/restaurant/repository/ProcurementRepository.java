package com.restaurant.repository;

import com.restaurant.entity.Procurement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProcurementRepository extends JpaRepository<Procurement, Long> {

    @Query("SELECT p FROM Procurement p WHERE p.p_date LIKE ?1%")
    List<Procurement> findByP_dateStartingWith(String month);

    @Query("SELECT p FROM Procurement p WHERE p.p_date BETWEEN ?1 AND ?2")
    List<Procurement> findByP_dateBetween(String start, String end);
}
