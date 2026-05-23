package com.restaurant.repository;

import com.restaurant.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    List<Attendance> findByDate(String date);
    Attendance findByPersonnelIdAndDate(Long personnelId, String date);
    List<Attendance> findByPersonnelIdAndDateBetween(Long personnelId, String start, String end);
    List<Attendance> findByDateBetween(String start, String end);
    List<Attendance> findByPersonnelId(Long personnelId);
}
