package com.example.demo.service.impl;

import com.example.demo.entity.Store;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.StoreRepository;
import com.example.demo.service.StoreService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoreServiceImpl implements StoreService {

    private final StoreRepository repo;

    public StoreServiceImpl(StoreRepository repo) {
        this.repo = repo;
    }

    public Store createStore(Store store) {
        repo.findByStoreName(store.getStoreName())
                .ifPresent(s -> { throw new BadRequestException("Store name already exists"); });
        return repo.save(store);
    }

    public Store getStoreById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Store not found"));
    }

    public List<Store> getAllStores() {
        return repo.findAll();
    }

    public Store updateStore(Long id, Store update) {
        Store existing = getStoreById(id);
        existing.setStoreName(update.getStoreName());
        existing.setAddress(update.getAddress());
        existing.setRegion(update.getRegion());
        existing.setActive(update.isActive());
        return repo.save(existing);
    }

    public void deactivateStore(Long id) {
        Store store = getStoreById(id);
        store.setActive(false);
        repo.save(store);
    }
}