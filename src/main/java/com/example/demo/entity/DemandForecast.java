package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(
    name = "demand_forecasts",
    uniqueConstraints = @UniqueConstraint(
        columnNames = {"product_id", "store_id", "forecast_date"}
    )
)
public class DemandForecast {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(optional = false)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @Column(name = "forecast_date", nullable = false)
    private LocalDate forecastDate;

    @Column(name = "predicted_demand", nullable = false)
    private Integer forecastedDemand;

    @Column(nullable = false)
    private Double confidenceScore = 1.0;

    @PrePersist
    public void initDefaults() {
        if (forecastDate == null) {
            forecastDate = LocalDate.now();
        }
        if (forecastedDemand == null) {
            forecastedDemand = 0;
        }
        if (confidenceScore == null) {
            confidenceScore = 1.0;
        }
    }

    // getters & setters
    public Long getId() { return id; }
    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }
    public Store getStore() { return store; }
    public void setStore(Store store) { this.store = store; }
    public LocalDate getForecastDate() { return forecastDate; }
    public void setForecastDate(LocalDate forecastDate) { this.forecastDate = forecastDate; }
    public Integer getForecastedDemand() { return forecastedDemand; }
    public void setForecastedDemand(Integer forecastedDemand) { this.forecastedDemand = forecastedDemand; }
    public Double getConfidenceScore() { return confidenceScore; }
    public void setConfidenceScore(Double confidenceScore) { this.confidenceScore = confidenceScore; }
}
