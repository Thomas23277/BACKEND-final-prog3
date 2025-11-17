package com.foodstore.htmeleros.controller;

import java.util.List;

import com.foodstore.htmeleros.dto.DetallePedidoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.foodstore.htmeleros.entity.DetallePedido;
import com.foodstore.htmeleros.service.DetallePedidoService;

@RestController
@RequestMapping("/detalles")
public class DetallePedidoController {

    private final DetallePedidoService detalleService;

    public DetallePedidoController(DetallePedidoService detalleService) {
        this.detalleService = detalleService;
    }

    @PostMapping
    public ResponseEntity<DetallePedidoDTO> save(@RequestBody DetallePedidoDTO dto) {
        return ResponseEntity.ok(detalleService.save(dto));
    }

    @GetMapping
    public ResponseEntity<List<DetallePedidoDTO>> findAll() {
        return ResponseEntity.ok(detalleService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetallePedidoDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(detalleService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DetallePedidoDTO> update(@PathVariable Long id, @RequestBody DetallePedidoDTO dto) {
        return ResponseEntity.ok(detalleService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        detalleService.deleteById(id);
    }
}

