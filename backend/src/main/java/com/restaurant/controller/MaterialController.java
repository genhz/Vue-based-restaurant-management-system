package com.restaurant.controller;

import com.restaurant.entity.Material;
import com.restaurant.service.MaterialService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class MaterialController {

    private final MaterialService materialService;

    public MaterialController(MaterialService materialService) {
        this.materialService = materialService;
    }

    @GetMapping("/getAllMaterial")
    public List<Material> getAllMaterial() {
        return materialService.getAllMaterial();
    }

    @PostMapping("/getAllMaterialById")
    public Material getAllMaterialById(@RequestBody Long id) {
        return materialService.getAllMaterialById(id);
    }

    @PostMapping("/addMaterial")
    public Material addMaterial(@RequestBody Material material) {
        return materialService.addMaterial(material);
    }

    @PostMapping("/editMaterial")
    public Material editMaterial(@RequestBody Material material) {
        return materialService.editMaterial(material);
    }

    @PostMapping("/removeMaterial")
    public void removeMaterial(@RequestBody Long id) {
        materialService.removeMaterial(id);
    }

    @GetMapping("/getPlanning")
    public List<Material> getPlanning() {
        return materialService.getPlanning();
    }

    @PostMapping("/commitWarehousing")
    public void commitWarehousing(@RequestBody List<Material> warehousing) {
        materialService.commitWarehousing(warehousing);
    }

    @PostMapping("/searchMaterialName")
    public List<Material> searchMaterialName(@RequestBody String name) {
        return materialService.searchMaterialName(stripJsonQuotes(name));
    }

    @PostMapping("/searchMaterialSupplier")
    public List<Material> searchMaterialSupplier(@RequestBody String supplier) {
        return materialService.searchMaterialSupplier(stripJsonQuotes(supplier));
    }

    private String stripJsonQuotes(String s) {
        if (s != null && s.startsWith("\"") && s.endsWith("\"")) {
            return s.substring(1, s.length() - 1);
        }
        return s;
    }

    @GetMapping("/getProcurelist")
    public List<Map<String, Object>> getProcurelist() {
        return materialService.getProcurelist();
    }

    @PostMapping("/getProcurelistmonth")
    public List<Map<String, Object>> getProcurelistmonth(@RequestBody String data) {
        return materialService.getProcurelistmonth(data);
    }
}
