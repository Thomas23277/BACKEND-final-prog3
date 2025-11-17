package com.foodstore.htmeleros.auth.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import com.foodstore.htmeleros.enums.Rol;
import com.foodstore.htmeleros.auth.dto.RegisterRequest;
import com.foodstore.htmeleros.auth.dto.UserResponse;
import com.foodstore.htmeleros.entity.Usuario;
import com.foodstore.htmeleros.repository.UsuarioRepository;
import com.foodstore.htmeleros.auth.util.Sha256Util;

import jakarta.transaction.Transactional;

@Service
public class AuthService {
    private final UsuarioRepository usuarioRepository;

    public AuthService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional
    public UserResponse register(RegisterRequest req) {
        if (usuarioRepository.existsByEmail(req.getEmail())) {
            throw new IllegalArgumentException("Email ya registrado");
        }

        Usuario usuario = new Usuario();
        usuario.setNombre(req.getNombre());
        usuario.setApellido(req.getApellido());
        usuario.setEmail(req.getEmail());
        usuario.setContrasenia(Sha256Util.hash(req.getContrasenia()));
        usuario.setRol(req.getRol() != null ? Rol.valueOf(req.getRol()) : Rol.USUARIO);

        Usuario saved = usuarioRepository.save(usuario);

        return new UserResponse(
                saved.getId(),
                saved.getNombre(),
                saved.getApellido(),
                saved.getEmail(),
                saved.getRol().name()
        );
    }

    public UserResponse login(String email, String contraseniaPlano) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(email);

        if (usuarioOpt.isEmpty()) {
            throw new IllegalArgumentException("Usuario no encontrado");
        }

        Usuario usuario = usuarioOpt.get();

        if (!usuario.getContrasenia().equals(Sha256Util.hash(contraseniaPlano))) {
            throw new IllegalArgumentException("Contraseña incorrecta");
        }

        return new UserResponse(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getApellido(),
                usuario.getEmail(),
                usuario.getRol().name()
        );
    }
}
