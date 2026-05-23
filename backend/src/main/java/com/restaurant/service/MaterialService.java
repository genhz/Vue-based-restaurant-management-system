package com.restaurant.service;

import com.restaurant.entity.*;
import com.restaurant.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MaterialService {

    private final MaterialRepository materialRepository;
    private final ProcurementRepository procurementRepository;
    private final ProcurementDetailRepository procurementDetailRepository;

    public MaterialService(MaterialRepository materialRepository,
                           ProcurementRepository procurementRepository,
                           ProcurementDetailRepository procurementDetailRepository) {
        this.materialRepository = materialRepository;
        this.procurementRepository = procurementRepository;
        this.procurementDetailRepository = procurementDetailRepository;
    }

    public List<Material> getAllMaterial() {
        return materialRepository.findAll();
    }

    public Material getAllMaterialById(Long id) {
        return materialRepository.findById(id).orElse(null);
    }

    @Transactional
    public Material addMaterial(Material material) {
        return materialRepository.save(material);
    }

    @Transactional
    public Material editMaterial(Material material) {
        Material existing = materialRepository.findById(material.getId()).orElse(null);
        if (existing != null) {
            existing.setM_name(material.getM_name());
            existing.setSupplier(material.getSupplier());
            existing.setPrice(material.getPrice());
            existing.setUnit(material.getUnit());
            existing.setBatch(material.getBatch());
            existing.setSafety_stock(material.getSafety_stock());
            existing.setStock(material.getStock());
            return materialRepository.save(existing);
        }
        return null;
    }

    @Transactional
    public void removeMaterial(Long id) {
        materialRepository.deleteById(id);
    }

    public List<Material> getPlanning() {
        List<Material> all = materialRepository.findAll();
        List<Material> planning = new ArrayList<>();
        for (Material m : all) {
            if (m.getSafety_stock() != null && m.getStock() != null
                    && m.getStock() < m.getSafety_stock()) {
                planning.add(m);
            }
        }
        return planning;
    }

    @Transactional
    public void commitWarehousing(List<Material> warehousing) {
        // Create procurement record
        Procurement procurement = new Procurement();
        procurement.setP_date(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        double total = 0;

        for (Material item : warehousing) {
            Material existing = materialRepository.findById(item.getId()).orElse(null);
            if (existing != null && item.getNumber() != null) {
                existing.setStock(existing.getStock() + item.getNumber());
                materialRepository.save(existing);
                total += item.getNumber() * (existing.getPrice() != null ? existing.getPrice() : 0);
            }
        }

        procurement.setP_total(total);
        procurement = procurementRepository.save(procurement);

        // Create procurement details
        for (Material item : warehousing) {
            Material existing = materialRepository.findById(item.getId()).orElse(null);
            if (existing != null && item.getNumber() != null) {
                ProcurementDetail detail = new ProcurementDetail();
                detail.setProcurement_id(procurement.getId());
                detail.setNumber(item.getNumber());
                detail.setPrice(existing.getPrice());
                detail.setMaterial(existing);
                procurementDetailRepository.save(detail);
            }
        }
    }

    public List<Material> searchMaterialName(String name) {
        return materialRepository.findByM_nameContaining("%" + name + "%");
    }

    public List<Material> searchMaterialSupplier(String supplier) {
        return materialRepository.findBySupplierContaining("%" + supplier + "%");
    }

    public List<Map<String, Object>> getProcurelist() {
        List<Procurement> list = procurementRepository.findAll();
        List<Map<String, Object>> result = new ArrayList<>();
        for (Procurement p : list) {
            Map<String, Object> map = new HashMap<>();
            map.put("date", p.getP_date());
            map.put("price", p.getP_total());
            result.add(map);
        }
        return result;
    }

    public List<Map<String, Object>> getProcurelistmonth(String data) {
        List<Procurement> procurements = procurementRepository
                .findByP_dateStartingWith(data);
        List<Long> ids = procurements.stream()
                .map(Procurement::getId).collect(Collectors.toList());

        if (ids.isEmpty()) return new ArrayList<>();

        List<ProcurementDetail> details = procurementDetailRepository
                .findByProcurement_idIn(ids);

        List<Map<String, Object>> result = new ArrayList<>();
        for (ProcurementDetail detail : details) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", detail.getId());
            map.put("number", detail.getNumber());
            map.put("price", detail.getPrice());
            // Wrap material in a list for frontend compatibility (materialList[0])
            List<Map<String, Object>> materialList = new ArrayList<>();
            if (detail.getMaterial() != null) {
                Map<String, Object> matMap = new HashMap<>();
                Material m = detail.getMaterial();
                matMap.put("m_name", m.getM_name());
                matMap.put("price", m.getPrice());
                matMap.put("safety_stock", m.getSafety_stock());
                matMap.put("stock", m.getStock());
                matMap.put("unit", m.getUnit());
                matMap.put("supplier", m.getSupplier());
                materialList.add(matMap);
            }
            map.put("materialList", materialList);
            result.add(map);
        }
        return result;
    }
}
