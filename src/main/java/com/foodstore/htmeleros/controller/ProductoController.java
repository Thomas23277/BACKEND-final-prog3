package com.foodstore.htmeleros.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import com.foodstore.htmeleros.dto.ProductoDTO;
import com.foodstore.htmeleros.dto.CategoriaDTO;
import com.foodstore.htmeleros.service.CategoriaService;
import com.foodstore.htmeleros.service.ProductoService;

@RestController
@RequestMapping("/api/producto")
public class ProductoController {

    private final ProductoService productoService;
    private final CategoriaService categoriaService;

    public ProductoController(ProductoService productoService, CategoriaService categoriaService) {
        this.productoService = productoService;
        this.categoriaService = categoriaService;
    }

    // Crear producto
    @PostMapping
    public ResponseEntity<ProductoDTO> save(@RequestBody ProductoDTO productoDTO) {
        Long categoriaId = productoDTO.getCategoria().getId();
        CategoriaDTO categoriaDTO = categoriaService.findById(categoriaId);
        productoDTO.setCategoria(categoriaDTO);

        ProductoDTO creado = productoService.save(productoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    // Buscar productos por categoría (usando service directamente)
    @GetMapping("/categoria/{categoriaId}")
    public ResponseEntity<List<ProductoDTO>> findByCategoria(@PathVariable Long categoriaId) {
        List<ProductoDTO> productos = productoService.findByCategoria(categoriaId);
        return ResponseEntity.ok(productos);
    }

    // Listar todos los productos
    @GetMapping
    public ResponseEntity<List<ProductoDTO>> findAll() {
        return ResponseEntity.ok(productoService.findAll());
    }

    // Obtener producto por ID
    @GetMapping("/{id}")
    public ResponseEntity<ProductoDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(productoService.findById(id));
    }

    // Eliminar producto
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        productoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Actualizar producto
    @PutMapping
    public ResponseEntity<ProductoDTO> updateProducto(@RequestBody ProductoDTO productoDTO) {
        ProductoDTO actualizado = productoService.update(productoDTO);
        return ResponseEntity.ok(actualizado);
    }

    // Vender producto
    @PostMapping("/{id}/vender/{cantidad}")
    public ResponseEntity<ProductoDTO> vender(@PathVariable Long id, @PathVariable int cantidad) {
        ProductoDTO vendido = productoService.venderProducto(id, cantidad);
        return ResponseEntity.ok(vendido);
    }

    // Agregar stock
    @PostMapping("/{id}/agregar-stock/{cantidad}")
    public ResponseEntity<ProductoDTO> agregarStock(@PathVariable Long id, @PathVariable int cantidad) {
        ProductoDTO actualizado = productoService.agregarStock(id, cantidad);
        return ResponseEntity.ok(actualizado);
    }
}
