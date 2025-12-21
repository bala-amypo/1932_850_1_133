package com.example.demo.repository;

import com.example.demo.entity.DemandForecast;
import com.example.demo.entity.Store;
import com.example.demo.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DemandForecastRepository extends JpaRepository<DemandForecast, Long> {
    List<DemandForecast> findByStoreAndProductAndForecastDateAfter(Store store, Product product, LocalDate date);
    List<DemandForecast> findByStore_Id(Long storeId);
}