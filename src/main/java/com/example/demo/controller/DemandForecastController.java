package com.example.demo.controller;

import com.example.demo.entity.DemandForecast;
import com.example.demo.service.DemandForecastService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/forecasts")
public class DemandForecastController {

    private final DemandForecastService service;

    public DemandForecastController(DemandForecastService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DemandForecast create(@RequestBody DemandForecast forecast) {
        return service.createForecast(forecast);
    }

    @GetMapping("/store/{storeId}")
    public List<DemandForecast> byStore(@PathVariable Long storeId) {
        return service.getForecastsForStore(storeId);
    }
}