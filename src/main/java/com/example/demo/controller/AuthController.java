package com.example.demo.controller;

import com.example.demo.dto.AuthRequestDto;
import com.example.demo.dto.AuthResponseDto;
import com.example.demo.dto.RegisterRequestDto;
import com.example.demo.entity.UserAccount;
import com.example.demo.repository.UserAccountRepository;
import com.example.demo.security.JwtUtil;
import com.example.demo.service.AuthService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService service;
    private final UserAccountRepository userAccountRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthController(AuthService service,
                          UserAccountRepository userAccountRepository,
                          PasswordEncoder passwordEncoder,
                          JwtUtil jwtUtil) {

        this.service = service;
        this.userAccountRepository = userAccountRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@RequestBody RegisterRequestDto dto) {
        service.register(dto);
    }

    @PostMapping("/login")
    public AuthResponseDto login(@RequestBody AuthRequestDto dto) {
        return service.login(dto);
    }

 
    @PostMapping("/init-admin")
    public ResponseEntity<?> initAdminUser() {

        Optional<UserAccount> existing =
                userAccountRepository.findByEmail("admin@example.com");

        if (existing.isEmpty()) {

            UserAccount admin = new UserAccount();
            admin.setEmail("admin@example.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRole("ADMIN");

            userAccountRepository.save(admin);
        }

        UserAccount adminUser =
                userAccountRepository.findByEmail("admin@example.com").get();

        String token = jwtUtil.generateToken(adminUser);

        Map<String, Object> body = new HashMap<>();
        body.put("token", token);

        return ResponseEntity.ok(body);
    }
}
