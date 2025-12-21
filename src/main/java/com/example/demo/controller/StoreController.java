package com.example.demo.controller;

import com.example.demo.entity.Store;
import com.example.demo.service.StoreService;

import java.util.List;

public class StoreController {

    private final StoreService storeService;

    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    public Store createStore(Store store) {
        return storeService.createStore(store);
    }

    public List<Store> getAllStores() {
        return storeService.getAllStores();
    }
}
