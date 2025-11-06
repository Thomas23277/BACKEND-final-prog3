package com.foodstore.htmeleros.auth.service;

import com.foodstore.htmeleros.auth.dto.RegisterRequest;
import com.foodstore.htmeleros.auth.dto.UserResponse;
import com.foodstore.htmeleros.auth.entity.User;
import com.foodstore.htmeleros.auth.repository.AuthUserRepository;
import com.foodstore.htmeleros.auth.util.Sha256Util;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthenticationService {
    private final AuthUserRepository userRepository;

    public AuthenticationService(AuthUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public UserResponse register(RegisterRequest req) {
        if (userRepository.existsByEmail(req.email())) {
            throw new IllegalArgumentException("Email already in use");
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
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Invalid credentials"));

        String hashed = Sha256Util.hash(password);
        if (!user.getPassword().equals(hashed)) {
            throw new IllegalArgumentException("Invalid credentials");
        }

        return new UserResponse(user.getId(), user.getName(), user.getEmail(), user.getRole());
    }
}
