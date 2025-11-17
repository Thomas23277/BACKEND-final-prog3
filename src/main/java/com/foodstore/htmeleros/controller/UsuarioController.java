package com.foodstore.htmeleros.controller;
import com.foodstore.htmeleros.auth.dto.LoginRequest;
import com.foodstore.htmeleros.dto.UsuarioDTO;
import com.foodstore.htmeleros.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/usuarios")

public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // Registro: solo nombre, apellido, email y contraseña (celular opcional)
    @PostMapping("/register")
    public ResponseEntity<UsuarioDTO> registrar(@Valid @RequestBody UsuarioDTO usuarioDTO) {
        UsuarioDTO guardado = usuarioService.save(usuarioDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(guardado);
    }

    // Login: email + contraseña en texto plano (el service compara SHA-256)
    @PostMapping("/login")
    public ResponseEntity<UsuarioDTO> login(@RequestBody LoginRequest request) {
        UsuarioDTO usuario = usuarioService.login(request.getEmail(), request.getContrasenia());
        return ResponseEntity.ok(usuario);
    }

    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> listarUsuarios() {
        return ResponseEntity.ok(usuarioService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> obtenerPorId(@PathVariable Long id) {
        UsuarioDTO usuario = usuarioService.findById(id);
        return ResponseEntity.ok(usuario);
    }

    @GetMapping("/buscar")
    public ResponseEntity<UsuarioDTO> buscarPorEmail(@RequestParam String email) {
        UsuarioDTO usuario = usuarioService.findByEmail(email);
        if (usuario != null) return ResponseEntity.ok(usuario);
        return ResponseEntity.notFound().build();
    }

}