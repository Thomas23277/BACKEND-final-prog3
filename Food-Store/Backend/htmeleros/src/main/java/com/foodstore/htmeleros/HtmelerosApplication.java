package com.foodstore.htmeleros;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.foodstore.htmeleros.entity.User;
import com.foodstore.htmeleros.repository.UserRepository;
import com.foodstore.htmeleros.util.Sha256Util;

@SpringBootApplication
public class HtmelerosApplication {

    public static void main(String[] args) {
        SpringApplication.run(HtmelerosApplication.class, args);
    }

    /**
     * Crea un usuario admin por defecto si no existe.
     * Email: user@admin.com
     * Password: Admin123
     */
    @Bean
    public CommandLineRunner createDefaultAdmin(UserRepository userRepository) {
        return args -> {
            final String adminEmail = "user@admin.com";
            final String adminPass = "Admin123";
            if (!userRepository.existsByEmail(adminEmail)) {
                User admin = User.builder()
                        .name("Admin")
                        .email(adminEmail)
                        .password(Sha256Util.hash(adminPass))
                        .role("admin")
                        .build();
                userRepository.save(admin);
                System.out.println("[FoodStore] Admin creado: " + adminEmail + " / " + adminPass);
            } else {
                System.out.println("[FoodStore] Admin ya existe: " + adminEmail);
            }
        };
    }
}
