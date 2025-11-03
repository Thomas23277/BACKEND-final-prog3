package com.foodstore.htmeleros.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.foodstore.htmeleros.entity.Producto;
import com.foodstore.htmeleros.repository.ProductoRepository;



@Service
public class ProductoServiceImpl implements ProductoService  {
    
    private final ProductoRepository productoRepository;
    private final CategoriaService categoriaService;

    public ProductoServiceImpl(ProductoRepository productoRepository, CategoriaService categoriaService) {
        this.productoRepository = productoRepository;
        this.categoriaService = categoriaService;
    }

    @Override
    public Producto save(Producto producto) {
        return productoRepository.save(producto);
    }

    @Override
    public List<Producto> findAll() {
        return productoRepository.findAll();
    }
    
    @Override
    public Producto findById(Long id) {
        return productoRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
    }

    
    @Override
    public void deleteById(Long id ) {
        productoRepository.deleteById(id);
    }

    

    @Override
    public Producto update(Producto producto) {
        return productoRepository.save(producto);
    }
}
