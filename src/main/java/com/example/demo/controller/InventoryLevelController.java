package com.example.demo.controller;

import com.example.demo.entity.InventoryLevel;
import com.example.demo.service.InventoryLevelService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
public class InventoryLevelController {

    private final InventoryLevelService inventoryService;

    public InventoryLevelController(InventoryLevelService inventoryService) {
        this.inventoryService = inventoryService;
    }package com.example.demo.controller;

import com.example.demo.entity.InventoryLevel;
import com.example.demo.service.InventoryLevelService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
public class InventoryLevelController {

    private final InventoryLevelService service;

    public InventoryLevelController(InventoryLevelService service) {
        this.service = service;
    }

    @PostMapping
    public InventoryLevel createOrUpdate(@RequestBody InventoryLevel inv) {
        return service.createOrUpdateInventory(inv);
    }

    @GetMapping("/store/{storeId}")
    public List<InventoryLevel> byStore(@PathVariable Long storeId) {
        return service.getInventoryForStore(storeId);
    }

    @GetMapping("/product/{productId}")
    public List<InventoryLevel> byProduct(@PathVariable Long productId) {
        return service.getInventoryForProduct(productId);
    }
}

    @PostMapping
    public InventoryLevel createOrUpdate(@RequestBody InventoryLevel inventory) {
        return inventoryService.createOrUpdateInventory(inventory);
    }

    @GetMapping("/store/{storeId}")
    public List<InventoryLevel> getInventoryForStore(@PathVariable Long storeId) {
        return inventoryService.getInventoryForStore(storeId);
    }

    @GetMapping("/product/{productId}")
    public List<InventoryLevel> getInventoryForProduct(@PathVariable Long productId) {
        return inventoryService.getInventoryForProduct(productId);
    }
}
