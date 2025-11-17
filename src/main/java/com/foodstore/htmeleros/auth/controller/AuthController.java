package com.foodstore.htmeleros.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.foodstore.htmeleros.auth.dto.LoginRequest;
import com.foodstore.htmeleros.auth.dto.RegisterRequest;
import com.foodstore.htmeleros.auth.dto.UserResponse;
import com.foodstore.htmeleros.auth.service.AuthService;

@CrossOrigin(origins = {
        "http://localhost:5173",
        "http://localhost:5174",
        "http://localhost:5175",
        "http://localhost:5176"
})
@RestController

@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        try {
            UserResponse user = authService.register(request);
            return ResponseEntity.status(201).body(user);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Server error");
        }
    }

    @PostMapping("/login")
public ResponseEntity<?> login(@RequestBody LoginRequest request) {
    try {
        UserResponse user = authService.login(request.getEmail(), request.getContrasenia());
        return ResponseEntity.ok(user);
    } catch (IllegalArgumentException e) {
        return ResponseEntity.status(401).body(e.getMessage());
    } catch (Exception e) {
        return ResponseEntity.internalServerError().body("Server error");
    }
}
}