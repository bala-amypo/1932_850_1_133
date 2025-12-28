package com.example.demo.service.impl;
import com.example.demo.entity.*;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.*;
import com.example.demo.service.InventoryBalancerService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.time.LocalDate;
import java.util.ArrayList; 
import java.util.List;


@Service
public class InventoryBalancerServiceImpl implements InventoryBalancerService {

    @Autowired private ProductRepository productRepository;
    @Autowired private InventoryLevelRepository inventoryLevelRepository;
    @Autowired private DemandForecastRepository demandForecastRepository;
    @Autowired private TransferSuggestionRepository transferSuggestionRepository;

    @Override
    public List<TransferSuggestion> generateSuggestions(Long productId) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        if (!product.isActive()) {
            throw new BadRequestException("Inactive product");
        }

        List<InventoryLevel> inventories =
                inventoryLevelRepository.findByProduct_Id(productId);

        List<DemandForecast> forecasts =
                demandForecastRepository.findByProduct_Id(productId);

        if (inventories.isEmpty() || forecasts.isEmpty()) {
            return List.of();
        }

        InventoryLevel overStore = null;
        InventoryLevel underStore = null;
        int maxSurplus = 0;
        int maxShortage = 0;

        for (InventoryLevel inv : inventories) {

            int demand = forecasts.stream()
                    .filter(f -> f.getStore().getId().equals(inv.getStore().getId()))
                    .mapToInt(DemandForecast::getForecastedDemand)
                    .sum();

            int diff = inv.getQuantity() - demand;

            // store with highest positive surplus
            if (diff > maxSurplus) {
                maxSurplus = diff;
                overStore = inv;
            }

            // store with greatest negative shortage
            if (diff < maxShortage) {
                maxShortage = diff;
                underStore = inv;
            }
        }

        if (overStore == null || underStore == null) {
            return List.of();
        }

        // quantity that can actually be meaningfully transferred
        // int qty = Math.min(maxSurplus, Math.abs(maxShortage));
        // if (qty <= 0) {
        //     return List.of();
        // }
        int qty = Math.min(maxSurplus, Math.abs(maxShortage));
        if (qty <= 0) qty = 1;

        TransferSuggestion ts = new TransferSuggestion();
        ts.setProduct(product);
        ts.setSourceStore(overStore.getStore());
        ts.setTargetStore(underStore.getStore());
        ts.setSuggestedQuantity(qty);
        ts.setReason("Auto balance - move surplus to deficit");
        // status + generatedAt handled by defaults / @PrePersist

        transferSuggestionRepository.saveAndFlush(ts);
        return List.of(ts);
    }

    @Override
    public TransferSuggestion getSuggestionById(Long id) {
        return transferSuggestionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Suggestion not found"));
    }

    @Override
    public List<TransferSuggestion> getSuggestionsForStore(Long storeId) {
        return transferSuggestionRepository.findBySourceStoreId(storeId);
    }
}
