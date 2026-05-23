package com.restaurant.service;

import com.restaurant.entity.Attendance;
import com.restaurant.entity.Personnel;
import com.restaurant.repository.AttendanceRepository;
import com.restaurant.repository.PersonnelRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class PersonnelService {

    private final PersonnelRepository personnelRepository;
    private final AttendanceRepository attendanceRepository;

    public PersonnelService(PersonnelRepository personnelRepository,
                            AttendanceRepository attendanceRepository) {
        this.personnelRepository = personnelRepository;
        this.attendanceRepository = attendanceRepository;
    }

    public List<Personnel> getAllPersonnel() {
        return personnelRepository.findAll();
    }

    public Personnel getPersonnelById(Long id) {
        return personnelRepository.findById(id).orElse(null);
    }

    @Transactional
    public Personnel addPersonnel(Personnel personnel) {
        return personnelRepository.save(personnel);
    }

    @Transactional
    public Personnel editPersonnel(Personnel personnel) {
        Personnel existing = personnelRepository.findById(personnel.getId()).orElse(null);
        if (existing != null) {
            existing.setP_name(personnel.getP_name());
            existing.setUsername(personnel.getUsername());
            existing.setPassword(personnel.getPassword());
            existing.setPhone(personnel.getPhone());
            existing.setP_type(personnel.getP_type());
            existing.setRole(personnel.getRole());
            existing.setB_salary(personnel.getB_salary());
            return personnelRepository.save(existing);
        }
        return null;
    }

    @Transactional
    public void removePersonnel(Long id) {
        personnelRepository.deleteById(id);
    }

    public Personnel login(String username, String password) {
        Personnel p = personnelRepository.findByUsername(username);
        if (p != null && p.getPassword().equals(password)) {
            return p;
        }
        return null;
    }

    public List<Personnel> searchPersonnelName(String name) {
        return personnelRepository.findByP_nameContaining("%" + name + "%");
    }

    public List<Personnel> searchPersonnelType(String type) {
        return personnelRepository.findByP_typeContaining("%" + type + "%");
    }

    @Transactional
    public Boolean attendance(List<Long> ids) {
        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        // check if already recorded today
        List<Attendance> todayRecords = attendanceRepository.findByDate(today);
        if (todayRecords != null && !todayRecords.isEmpty()) {
            return false;
        }
        for (Long id : ids) {
            Attendance a = new Attendance();
            a.setPersonnelId(id);
            a.setDate(today);
            attendanceRepository.save(a);
        }
        return true;
    }
}
