package com.foodstore.htmeleros.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.foodstore.htmeleros.entity.DetallePedido;
import com.foodstore.htmeleros.entity.Pedido;
import com.foodstore.htmeleros.entity.Usuario;
import com.foodstore.htmeleros.repository.UsuarioRepository;
import com.foodstore.htmeleros.service.PedidoService;

@RestController
@RequestMapping("/api/pedidos")
@CrossOrigin(origins = "http://localhost:8080")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private UsuarioRepository usuarioRepository;
    

       // Crear nuevo pedido
    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Pedido pedido) {
        try {
            //Validar usuario
            if (pedido.getUsuario() == null || pedido.getUsuario().getId() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("El pedido debe incluir un usuario con un ID válido.");
            }

            Long userId = pedido.getUsuario().getId();

            //Buscar usuario real en la BD
            Optional<Usuario> usuarioOpt = usuarioRepository.findById(userId);

            if (usuarioOpt.isEmpty()) {
                //Si el usuario no existe, intentamos sincronizar por email
                //Esto evita errores cuando el frontend guarda el usuario en localStorage
                //pero el backend no lo reconoce todavía.
                String email = pedido.getUsuario().getEmail();
                if (email != null) {
                    Optional<Usuario> usuarioPorEmail = usuarioRepository.findByEmail(email);
                    if (usuarioPorEmail.isPresent()) {
                        pedido.setUsuario(usuarioPorEmail.get());
                    } else {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                .body("El usuario con ID " + userId + " no existe en la base de datos.");
                    }
                } else {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body("El usuario con ID " + userId + " no existe y no se pudo validar por email.");
                }
            } else {
                //Asignar el usuario existente
                pedido.setUsuario(usuarioOpt.get());
            }

            //Vincular detalles al pedido
            if (pedido.getDetalles() != null && !pedido.getDetalles().isEmpty()) {
                for (DetallePedido detalle : pedido.getDetalles()) {
                    detalle.setPedido(pedido);
                }
            }

            //Guardar pedido completo
            Pedido creado = pedidoService.save(pedido);
            return ResponseEntity.status(HttpStatus.CREATED).body(creado);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al crear el pedido: " + e.getMessage());
        }
    }

    //Listar todos los pedidos
    @GetMapping
    public ResponseEntity<List<Pedido>> listar() {
        return ResponseEntity.ok(pedidoService.findAll());
    }

    //Obtener un pedido por ID
    @GetMapping("/{id}")
    public ResponseEntity<Pedido> obtener(@PathVariable Long id) {
        Pedido pedido = pedidoService.findById(id);
        if (pedido != null) {
            return ResponseEntity.ok(pedido);
        }
        return ResponseEntity.notFound().build();
    }

    //Actualizar pedido existente
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody Pedido pedido) {
        try {
            Pedido actualizado = pedidoService.update(id, pedido);
            return ResponseEntity.ok(actualizado);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al actualizar el pedido: " + e.getMessage());
        }
    }

    //Eliminar pedido por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            pedidoService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al eliminar el pedido: " + e.getMessage());
        }
    }
}

