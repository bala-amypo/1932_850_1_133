package com.example.demo.service.impl;

import com.example.demo.entity.DemandForecast;
import com.example.demo.exception.BadRequestException;
import com.example.demo.repository.DemandForecastRepository;
import com.example.demo.service.DemandForecastService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class DemandForecastServiceImpl implements DemandForecastService {
    private final DemandForecastRepository demandForecastRepository;
    
    public DemandForecastServiceImpl(DemandForecastRepository demandForecastRepository) {
        this.demandForecastRepository = demandForecastRepository;
    }
    
    @Override
    public DemandForecast createForecast(DemandForecast forecast) {
        if (forecast.getForecastDate().isBefore(LocalDate.now()) || 
            forecast.getForecastDate().isEqual(LocalDate.now())) {
            throw new BadRequestException("Forecast date must be in the future");
        }
        
        if (forecast.getPredictedDemand() < 0) {
            throw new BadRequestException("Predicted demand must be non-negative");
        }
        
        return demandForecastRepository.save(forecast);
    }
    
    @Override
    public List<DemandForecast> getForecastsForStore(Long storeId) {
        return demandForecastRepository.findByStore_Id(storeId);
    }
    
    @Override
    public DemandForecast getForecast(Long storeId, Long productId) {
        // This is a simplified implementation - in practice you might want more specific queries
        return null;
    }
}