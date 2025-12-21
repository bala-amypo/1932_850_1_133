package com.example.demo.service.impl;

import com.example.demo.entity.*;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.*;
import com.example.demo.service.InventoryBalancerService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class InventoryBalancerServiceImpl implements InventoryBalancerService {
    private final TransferSuggestionRepository transferSuggestionRepository;
    private final InventoryLevelRepository inventoryLevelRepository;
    private final DemandForecastRepository demandForecastRepository;
    private final StoreRepository storeRepository;
    
    public InventoryBalancerServiceImpl(TransferSuggestionRepository transferSuggestionRepository,
                                       InventoryLevelRepository inventoryLevelRepository,
                                       DemandForecastRepository demandForecastRepository,
                                       StoreRepository storeRepository) {
        this.transferSuggestionRepository = transferSuggestionRepository;
        this.inventoryLevelRepository = inventoryLevelRepository;
        this.demandForecastRepository = demandForecastRepository;
        this.storeRepository = storeRepository;
    }
    
    @Override
    public List<TransferSuggestion> generateSuggestions(Long productId) {
        List<InventoryLevel> inventories = inventoryLevelRepository.findByProduct_Id(productId);
        
        if (inventories.isEmpty()) {
            throw new BadRequestException("No inventory found for product");
        }
        
        Product product = inventories.get(0).getProduct();
        
        if (!product.getActive()) {
            throw new BadRequestException("Product is not active");
        }
        
        List<TransferSuggestion> suggestions = new ArrayList<>();
        
        // Find stores with excess and deficit
        InventoryLevel maxInv = null;
        InventoryLevel minInv = null;
        
        for (InventoryLevel inv : inventories) {
            List<DemandForecast> forecasts = demandForecastRepository
                    .findByStoreAndProductAndForecastDateAfter(inv.getStore(), product, LocalDate.now());
            
            if (forecasts.isEmpty()) {
                throw new BadRequestException("No forecast found");
            }
            
            if (maxInv == null || inv.getQuantity() > maxInv.getQuantity()) {
                maxInv = inv;
            }
            if (minInv == null || inv.getQuantity() < minInv.getQuantity()) {
                minInv = inv;
            }
        }
        
        // Create transfer suggestion if there's imbalance
        if (maxInv != null && minInv != null && !maxInv.equals(minInv)) {
            int transferQty = (maxInv.getQuantity() - minInv.getQuantity()) / 2;
            if (transferQty > 0) {
                TransferSuggestion suggestion = new TransferSuggestion(
                        maxInv.getStore(), minInv.getStore(), product, transferQty);
                suggestion.setPriority("HIGH");
                suggestions.add(transferSuggestionRepository.save(suggestion));
            }
        }
        
        return suggestions;
    }
    
    @Override
    public List<TransferSuggestion> getSuggestionsForStore(Long storeId) {
        return transferSuggestionRepository.findBySourceStoreId(storeId);
    }
    
    @Override
    public TransferSuggestion getSuggestionById(Long id) {
        return transferSuggestionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Suggestion not found"));
    }
}