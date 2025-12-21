package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "store")
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false)
    private String storeName;
    
    private String address;
    private String region;
    
    @Column(nullable = false)
    private Boolean active = true;
    
    // Constructors
    public Store() {}
    
    public Store(String storeName, String address, String region) {
        this.storeName = storeName;
        this.address = address;
        this.region = region;
        this.active = true;
    }
    
    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getStoreName() { return storeName; }
    public void setStoreName(String storeName) { this.storeName = storeName; }
    
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    
    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }
    
    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }
}