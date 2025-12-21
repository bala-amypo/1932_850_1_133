package com.example.demo.dto;

import java.time.LocalDateTime;

public class AuthResponseDto {
    private String token;
    private LocalDateTime expiresAt;
    
    // Constructors
    public AuthResponseDto() {}
    
    public AuthResponseDto(String token, LocalDateTime expiresAt) {
        this.token = token;
        this.expiresAt = expiresAt;
    }
    
    // Getters and setters
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
    
    public LocalDateTime getExpiresAt() { return expiresAt; }
    public void setExpiresAt(LocalDateTime expiresAt) { this.expiresAt = expiresAt; }
}