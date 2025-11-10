package com.foodstore.htmeleros.controller;

import com.foodstore.htmeleros.entity.Usuario;
import com.foodstore.htmeleros.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "http://localhost:5173") // ajusta si tu frontend usa otro puerto
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    /**
     * ✅ LOGIN: Solo permite iniciar sesión si el usuario existe
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestParam String email,
            @RequestParam String password
    ) {
        try {
            Optional<Usuario> existente = usuarioRepository.findByEmail(email);

            if (existente.isEmpty()) {
                return ResponseEntity.badRequest().body("Usuario no encontrado");
            }

            Usuario usuario = existente.get();

            if (!usuario.getPassword().equals(password)) {
                return ResponseEntity.badRequest().body("Contraseña incorrecta");
            }

            return ResponseEntity.ok(usuario);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error al iniciar sesión: " + e.getMessage());
        }
    }

    /**
     * ✅ REGISTRO: Crea un nuevo usuario solo si no existe
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(
            @RequestParam String email,
            @RequestParam String nombre,
            @RequestParam String password
    ) {
        try {
            Optional<Usuario> existente = usuarioRepository.findByEmail(email);

            if (existente.isPresent()) {
                return ResponseEntity.badRequest().body("El email ya está registrado");
            }

            Usuario nuevo = new Usuario();
            nuevo.setNombre(nombre);
            nuevo.setEmail(email);
            nuevo.setPassword(password);

            Usuario guardado = usuarioRepository.save(nuevo);
            return ResponseEntity.ok(guardado);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error al registrar usuario: " + e.getMessage());
        }
    }

    /**
     * ✅ Listar todos los usuarios
     */
    @GetMapping
    public ResponseEntity<Object> listarUsuarios() {
        return ResponseEntity.ok(usuarioRepository.findAll());
    }

    /**
     * ✅ Obtener un usuario por su ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Object> obtenerPorId(@PathVariable Long id) {
        return usuarioRepository.findById(id)
                .<ResponseEntity<Object>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().body("Usuario no encontrado"));
    }
}
