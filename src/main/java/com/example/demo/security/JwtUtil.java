package com.example.demo.security;

import com.example.demo.entity.UserAccount;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component
public class JwtUtil {
    private final SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private final int expiration = 86400; // 24 hours in seconds
    
    public String generateToken(UserAccount user) {
        Date expiryDate = Date.from(LocalDateTime.now().plusSeconds(expiration)
                .atZone(ZoneId.systemDefault()).toInstant());
        
        return Jwts.builder()
                .setSubject(user.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .claim("role", user.getRole())
                .signWith(key)
                .compact();
    }
    
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    public String getEmailFromToken(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token).getBody();
        return claims.getSubject();
    }
    
    public LocalDateTime getExpirationTime() {
        return LocalDateTime.now().plusSeconds(expiration);
    }
}