package com.example.demo.service.impl;

import com.example.demo.entity.InventoryLevel;
import com.example.demo.entity.Product;
import com.example.demo.entity.Store;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.InventoryLevelRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.StoreRepository;
import com.example.demo.service.InventoryService;

public class InventoryServiceImpl implements InventoryService {

    private final InventoryLevelRepository inventoryRepo;
    private final StoreRepository storeRepo;
    private final ProductRepository productRepo;

    public InventoryServiceImpl(
            InventoryLevelRepository inventoryRepo,
            StoreRepository storeRepo,
            ProductRepository productRepo) {
        this.inventoryRepo = inventoryRepo;
        this.storeRepo = storeRepo;
        this.productRepo = productRepo;
    }

    @Override
    public InventoryLevel updateInventory(Long storeId, Long productId, Integer quantity) {

        if (quantity < 0) {
            throw new IllegalArgumentException("Invalid Quantity: Quantity must be >= 0");
        }

        Store store = storeRepo.findById(storeId)
                .orElseThrow(() -> new ResourceNotFoundException("Store not found"));

        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        InventoryLevel inventory = new InventoryLevel();
        inventory.setStore(store);
        inventory.setProduct(product);
        inventory.setQuantity(quantity);

        return inventoryRepo.save(inventory);
    }
}
