package com.restaurant.config;

import com.restaurant.entity.*;
import com.restaurant.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DataInitializer implements CommandLineRunner {

    private final PersonnelRepository personnelRepository;
    private final MaterialRepository materialRepository;
    private final DishesRepository dishesRepository;

    public DataInitializer(PersonnelRepository personnelRepository,
                           MaterialRepository materialRepository,
                           DishesRepository dishesRepository) {
        this.personnelRepository = personnelRepository;
        this.materialRepository = materialRepository;
        this.dishesRepository = dishesRepository;
    }

    @Override
    @Transactional
    public void run(String... args) {
        if (personnelRepository.count() > 0) {
            return;
        }

        // Demo personnel
        createPersonnel("管理员", "admin", "admin", "13800000001", "管理员", "admin", 8000.0);
        createPersonnel("前台张三", "counter", "counter", "13800000002", "前台", "counter", 5000.0);
        createPersonnel("采购李四", "procure", "procure", "13800000003", "采购人员", "procure", 5500.0);
        createPersonnel("服务员王五", "waiter", "waiter", "13800000004", "服务员", "user", 4000.0);

        // Demo materials
        createMaterial("牛肉", "食品供应商A", 30.0, "kg", 20, 10, 25);
        createMaterial("猪肉", "食品供应商A", 20.0, "kg", 20, 10, 30);
        createMaterial("鸡肉", "食品供应商B", 15.0, "kg", 15, 8, 20);
        createMaterial("白菜", "蔬菜供应商", 3.0, "kg", 30, 15, 40);
        createMaterial("土豆", "蔬菜供应商", 2.5, "kg", 40, 20, 50);
        createMaterial("食用油", "调料供应商", 12.0, "L", 10, 5, 15);
        createMaterial("盐", "调料供应商", 3.0, "kg", 30, 10, 25);
        createMaterial("酱油", "调料供应商", 8.0, "L", 15, 5, 12);

        // Demo dishes
        createDish("红烧牛肉", "肉类", "default.jpg", 58.0, 0, true);
        createDish("宫保鸡丁", "肉类", "default.jpg", 38.0, 0, true);
        createDish("清炒白菜", "素菜", "default.jpg", 18.0, 0, true);
        createDish("酸辣土豆丝", "素菜", "default.jpg", 16.0, 0, true);
        createDish("红烧肉", "肉类", "default.jpg", 48.0, 0, true);
        createDish("番茄蛋汤", "汤类", "default.jpg", 22.0, 0, true);
        createDish("蛋炒饭", "主食", "default.jpg", 15.0, 0, true);
        createDish("牛肉面", "主食", "default.jpg", 25.0, 0, true);
    }

    private void createPersonnel(String name, String username, String password,
                                  String phone, String type, String role, Double salary) {
        Personnel p = new Personnel();
        p.setP_name(name);
        p.setUsername(username);
        p.setPassword(password);
        p.setPhone(phone);
        p.setP_type(type);
        p.setRole(role);
        p.setB_salary(salary);
        personnelRepository.save(p);
    }

    private void createMaterial(String name, String supplier, Double price,
                                 String unit, Integer batch, Integer safetyStock, Integer stock) {
        Material m = new Material();
        m.setM_name(name);
        m.setSupplier(supplier);
        m.setPrice(price);
        m.setUnit(unit);
        m.setBatch(batch);
        m.setSafety_stock(safetyStock);
        m.setStock(stock);
        materialRepository.save(m);
    }

    private void createDish(String name, String type, String image,
                             Double price, Integer salesVolume, Boolean state) {
        Dishes d = new Dishes();
        d.setName(name);
        d.setType(type);
        d.setImage(image);
        d.setPrice(price);
        d.setSales_volume(salesVolume);
        d.setState(state);
        dishesRepository.save(d);
    }
}
