package com.restaurant.repository;

import com.restaurant.entity.ProcurementDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProcurementDetailRepository extends JpaRepository<ProcurementDetail, Long> {

    @Query("SELECT pd FROM ProcurementDetail pd WHERE pd.procurement_id = ?1")
    List<ProcurementDetail> findByProcurement_id(Long procurementId);

    @Query("SELECT pd FROM ProcurementDetail pd WHERE pd.procurement_id IN ?1")
    List<ProcurementDetail> findByProcurement_idIn(List<Long> procurementIds);
}
