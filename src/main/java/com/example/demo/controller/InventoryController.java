package com.example.demo.controller;

import com.example.demo.entity.InventoryLevel;
import com.example.demo.service.InventoryService;

public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    public InventoryLevel updateInventory(
            Long storeId,
            Long productId,
            Integer quantity) {

        return inventoryService.updateInventory(storeId, productId, quantity);
    }
}
