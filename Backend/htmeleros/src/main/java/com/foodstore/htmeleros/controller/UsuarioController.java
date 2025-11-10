package com.foodstore.htmeleros.controller;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.foodstore.htmeleros.entity.Usuario;
import com.foodstore.htmeleros.repository.UsuarioRepository;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "http://localhost:5173") // ajusta si tu frontend usa otro puerto
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    /**
     * Registrar o recuperar usuario existente por email.
     * Si ya existe, lo devuelve. Si no existe, lo crea con el nombre recibido.
     */
    @PostMapping("/login")
    public ResponseEntity<?> loginORegistrar(
            @RequestParam String email,
            @RequestParam(required = false) String nombre
    ) {
        try {
            //Buscar usuario existente
            Optional<Usuario> existente = usuarioRepository.findByEmail(email);

            if (existente.isPresent()) {
                //Devuelve el usuario con ID correcto
                return ResponseEntity.ok(existente.get());
            }

            //Crear nuevo usuario si no existe
            if (nombre == null || nombre.isBlank()) {
                nombre = "Invitado";
            }

            Usuario nuevo = new Usuario();
            nuevo.setNombre(nombre);
            nuevo.setEmail(email);

            //Guardar y devolver el objeto con ID generado por JPA
            Usuario guardado = usuarioRepository.save(nuevo);
            return ResponseEntity.ok(guardado);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest()
                    .body("Error al registrar o recuperar usuario: " + e.getMessage());
        }
    }

    /**
     * Listar todos los usuarios
     */
    @GetMapping
    public ResponseEntity<Object> listarUsuarios() {
        return ResponseEntity.ok(usuarioRepository.findAll());
    }

    /**
     * Obtener un usuario por su ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Object> obtenerPorId(@PathVariable Long id) {
        return usuarioRepository.findById(id)
                .<ResponseEntity<Object>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().body("Usuario no encontrado"));
    }
}


