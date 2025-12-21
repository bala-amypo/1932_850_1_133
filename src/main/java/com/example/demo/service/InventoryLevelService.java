package com.example.demo.service;

import com.example.demo.entity.InventoryLevel;

public interface InventoryService {
    InventoryLevel updateInventory(Long storeId, Long productId, Integer quantity);
}
