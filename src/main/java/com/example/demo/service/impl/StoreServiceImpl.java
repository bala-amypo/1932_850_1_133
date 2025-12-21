package com.example.demo.service.impl;

import com.example.demo.entity.Store;
import com.example.demo.exception.BadRequestException;
import com.example.demo.repository.StoreRepository;
import com.example.demo.service.StoreService;

public class StoreServiceImpl implements StoreService {

    private final StoreRepository storeRepository;

    public StoreServiceImpl(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    @Override
    public Store createStore(Store store) {
        if (storeRepository.existsByStoreName(store.getStoreName())) {
            throw new BadRequestException("Duplicate Store: Store name already exists");
        }
        return storeRepository.save(store);
    }

    @Override
    public java.util.List<Store> getAllStores() {
        return storeRepository.findAll();
    }
}
