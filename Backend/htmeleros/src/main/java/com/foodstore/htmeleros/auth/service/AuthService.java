package com.foodstore.htmeleros.auth.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.foodstore.htmeleros.auth.dto.RegisterRequest;
import com.foodstore.htmeleros.auth.dto.UserResponse;
import com.foodstore.htmeleros.auth.entity.User;
import com.foodstore.htmeleros.auth.repository.UserRepository;
import com.foodstore.htmeleros.auth.util.Sha256Util;

import jakarta.transaction.Transactional;

@Service
public class AuthService {
    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public UserResponse register(RegisterRequest req) {
        if (userRepository.existsByEmail(req.email())) {
            throw new IllegalArgumentException("Email ya registrado");
        }

        User user = User.builder()
                .name(req.name())
                .email(req.email())
                .password(Sha256Util.hash(req.password()))
                .role("client")
                .build();

        User saved = userRepository.save(user);
        return new UserResponse(saved.getId(), saved.getName(), saved.getEmail(), saved.getRole());
    }

    public UserResponse login(String email, String password) {
        Optional<User> user = userRepository.findByEmail(email);

        if (user.isEmpty()) {
            throw new IllegalArgumentException("Usuario no encontrado");
        }

        User u = user.get();

        if (!u.getPassword().equals(Sha256Util.hash(password))) {
            throw new IllegalArgumentException("Contraseña incorrecta");
        }

        return new UserResponse(u.getId(), u.getName(), u.getEmail(), u.getRole());
    }
}