package com.example.demo.service.impl;

import com.example.demo.entity.InventoryLevel;
import com.example.demo.entity.Store;
import com.example.demo.entity.Product;
import com.example.demo.exception.BadRequestException;
import com.example.demo.repository.InventoryLevelRepository;
import com.example.demo.repository.StoreRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.InventoryLevelService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryLevelServiceImpl implements InventoryLevelService {
    private final InventoryLevelRepository inventoryLevelRepository;
    private final StoreRepository storeRepository;
    private final ProductRepository productRepository;
    
    public InventoryLevelServiceImpl(InventoryLevelRepository inventoryLevelRepository,
                                    StoreRepository storeRepository,
                                    ProductRepository productRepository) {
        this.inventoryLevelRepository = inventoryLevelRepository;
        this.storeRepository = storeRepository;
        this.productRepository = productRepository;
    }
    
    @Override
    public InventoryLevel createOrUpdateInventory(InventoryLevel inv) {
        if (inv.getQuantity() < 0) {
            throw new BadRequestException("Quantity must be >= 0");
        }
        
        InventoryLevel existing = inventoryLevelRepository.findByStoreAndProduct(
                inv.getStore(), inv.getProduct());
        
        if (existing != null) {
            existing.setQuantity(inv.getQuantity());
            return inventoryLevelRepository.save(existing);
        }
        
        return inventoryLevelRepository.save(inv);
    }
    
    @Override
    public List<InventoryLevel> getInventoryForStore(Long storeId) {
        return inventoryLevelRepository.findByStore_Id(storeId);
    }
    
    @Override
    public List<InventoryLevel> getInventoryForProduct(Long productId) {
        return inventoryLevelRepository.findByProduct_Id(productId);
    }
    
    @Override
    public InventoryLevel getInventory(Long storeId, Long productId) {
        Store store = storeRepository.findById(storeId).orElse(null);
        Product product = productRepository.findById(productId).orElse(null);
        
        if (store == null || product == null) {
            return null;
        }
        
        return inventoryLevelRepository.findByStoreAndProduct(store, product);
    }
}