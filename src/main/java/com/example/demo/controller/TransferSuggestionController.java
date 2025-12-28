package com.example.demo.controller;

import com.example.demo.entity.TransferSuggestion;
import com.example.demo.service.InventoryBalancerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/suggestions")
public class TransferSuggestionController {

    private final InventoryBalancerService service;

    public TransferSuggestionController(InventoryBalancerService service) {
        this.service = service;
    }

    @PostMapping("/generate/{productId}")
    public List<TransferSuggestion> generate(@PathVariable Long productId) {
        return service.generateSuggestions(productId);
    }

    @GetMapping("/store/{storeId}")
    public List<TransferSuggestion> byStore(@PathVariable Long storeId) {
        return service.getSuggestionsForStore(storeId);
    }

    @GetMapping("/{id}")
    public TransferSuggestion get(@PathVariable Long id) {
        return service.getSuggestionById(id);
    }
}