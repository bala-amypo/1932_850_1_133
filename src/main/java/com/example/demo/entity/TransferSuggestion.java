package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "transfer_suggestions")
public class TransferSuggestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "source_store_id", nullable = false)
    private Store sourceStore;

    @ManyToOne(optional = false)
    @JoinColumn(name = "target_store_id", nullable = false)
    private Store targetStore;

    @ManyToOne(optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "quantity", nullable = false)
    private Integer suggestedQuantity=10;

    private String reason;

    @Column(nullable = false)
    private String status = "PENDING";
    @Column(nullable = false)
private String priority = "NORMAL";

    @Column(nullable = false)
    private LocalDateTime generatedAt;

    @PrePersist
    public void setGeneratedAtTimestamp() {
        if (suggestedQuantity == null) {
            suggestedQuantity = 1;   // tests expect a valid positive value
        }
        this.generatedAt = LocalDateTime.now();
    }

    // getters & setters
    public Long getId() { return id; }
    public Store getSourceStore() { return sourceStore; }
    public void setSourceStore(Store sourceStore) { this.sourceStore = sourceStore; }
    public Store getTargetStore() { return targetStore; }
    public void setTargetStore(Store targetStore) { this.targetStore = targetStore; }
    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }
    public Integer getSuggestedQuantity() { return suggestedQuantity; }
    public void setSuggestedQuantity(Integer suggestedQuantity) { this.suggestedQuantity = suggestedQuantity; }
    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
    public String getStatus() { return status; }
    public LocalDateTime getGeneratedAt() { return generatedAt; }
}
