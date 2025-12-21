package com.example.demo.repository;

import com.example.demo.entity.InventoryLevel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InventoryLevelRepository extends JpaRepository<InventoryLevel, Long> {
    List<InventoryLevel> findByStoreId(Long storeId);
}
