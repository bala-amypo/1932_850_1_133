package com.example.demo.service.impl;

import com.example.demo.entity.InventoryLevel;
import com.example.demo.exception.BadRequestException;
import com.example.demo.repository.InventoryLevelRepository;
import com.example.demo.service.InventoryLevelService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryLevelServiceImpl implements InventoryLevelService {

    private final InventoryLevelRepository repo;

    public InventoryLevelServiceImpl(InventoryLevelRepository repo) {
        this.repo = repo;
    }

    public InventoryLevel createOrUpdateInventory(InventoryLevel inv) {
        if (inv.getQuantity() < 0) {
            throw new BadRequestException("Quantity must be >= 0");
        }

        return repo.findByStoreAndProduct(inv.getStore(), inv.getProduct())
                .map(existing -> {
                    existing.setQuantity(inv.getQuantity());
                    return repo.save(existing);
                })
                .orElseGet(() -> repo.save(inv));
    }

    public List<InventoryLevel> getInventoryForStore(Long storeId) {
        return repo.findByStore_Id(storeId);
    }

    public List<InventoryLevel> getInventoryForProduct(Long productId) {
        return repo.findByProduct_Id(productId);
    }
}