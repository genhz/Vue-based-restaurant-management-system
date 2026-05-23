package com.restaurant.controller;

import com.restaurant.entity.Personnel;
import com.restaurant.service.PersonnelService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PersonnelController {

    private final PersonnelService personnelService;

    public PersonnelController(PersonnelService personnelService) {
        this.personnelService = personnelService;
    }

    @GetMapping("/getAllPersonnel")
    public List<Personnel> getAllPersonnel() {
        return personnelService.getAllPersonnel();
    }

    @PostMapping("/getPersonnelById")
    public Personnel getPersonnelById(@RequestBody Long id) {
        return personnelService.getPersonnelById(id);
    }

    @PostMapping("/editPersonnel")
    public Personnel editPersonnel(@RequestBody Personnel personnel) {
        return personnelService.editPersonnel(personnel);
    }

    @PostMapping("/removePersonnel")
    public void removePersonnel(@RequestBody Long id) {
        personnelService.removePersonnel(id);
    }

    @PostMapping("/addPersonnel")
    public Personnel addPersonnel(@RequestBody Personnel personnel) {
        return personnelService.addPersonnel(personnel);
    }

    @PostMapping("/login")
    public Personnel login(@RequestBody Personnel personnel) {
        Personnel result = personnelService.login(personnel.getUsername(), personnel.getPassword());
        if (result == null) {
            return null;
        }
        return result;
    }

    @PostMapping("/searchPersonnelName")
    public List<Personnel> searchPersonnelName(@RequestBody String name) {
        return personnelService.searchPersonnelName(stripJsonQuotes(name));
    }

    @PostMapping("/searchPersonnelType")
    public List<Personnel> searchPersonnelType(@RequestBody String type) {
        return personnelService.searchPersonnelType(stripJsonQuotes(type));
    }

    private String stripJsonQuotes(String s) {
        if (s != null && s.startsWith("\"") && s.endsWith("\"")) {
            return s.substring(1, s.length() - 1);
        }
        return s;
    }

    @PostMapping("/Attendance")
    public Boolean attendance(@RequestBody List<Long> list) {
        return personnelService.attendance(list);
    }
}
