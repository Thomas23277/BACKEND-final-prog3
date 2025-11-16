package com.foodstore.htmeleros.service;

import java.util.List;

import com.foodstore.htmeleros.entity.Categoria;
import com.foodstore.htmeleros.repository.CategoriaRepository;
import org.springframework.stereotype.Service;

import com.foodstore.htmeleros.dto.ProductoDTO;
import com.foodstore.htmeleros.entity.Producto;
import com.foodstore.htmeleros.exception.ResourceNotFoundException;
import com.foodstore.htmeleros.mappers.ProductoMapper;
import com.foodstore.htmeleros.repository.ProductoRepository;

@Service
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;

    public ProductoServiceImpl(ProductoRepository productoRepository, CategoriaRepository categoriaRepository) {
        this.productoRepository = productoRepository;
        this.categoriaRepository = categoriaRepository;
    }


    @Override
    public ProductoDTO save(ProductoDTO dto) {
        Producto producto = ProductoMapper.toEntity(dto);
        Producto guardado = productoRepository.save(producto);
        return ProductoMapper.toDTO(guardado);
    }

    @Override
    public List<ProductoDTO> findAll() {
        return productoRepository.findAll().stream()
                .map(ProductoMapper::toDTO)
                .toList(); //
    }

    @Override
    public ProductoDTO findById(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado"));
        return ProductoMapper.toDTO(producto);
    }

    @Override
    public void deleteById(Long id) {
        productoRepository.deleteById(id);
    }

    @Override
    public ProductoDTO update(ProductoDTO dto) {
        Producto producto = ProductoMapper.toEntity(dto);
        Producto actualizado = productoRepository.save(producto);
        return ProductoMapper.toDTO(actualizado);
    }

    @Override
    public ProductoDTO venderProducto(Long productoId, int cantidad) {
        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado"));

        if (producto.getStock() == 0) {
            throw new IllegalStateException("No hay stock disponible para este producto");
        }

        if (cantidad > producto.getStock()) {
            throw new IllegalArgumentException("La cantidad solicitada supera el stock disponible");
        }

        producto.setStock(producto.getStock() - cantidad);
        Producto actualizado = productoRepository.save(producto);
        return ProductoMapper.toDTO(actualizado);
    }

    @Override
    public ProductoDTO agregarStock(Long productoId, int cantidad) {
        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado"));

        producto.setStock(producto.getStock() + cantidad);
        Producto actualizado = productoRepository.save(producto);
        return ProductoMapper.toDTO(actualizado);
    }
    @Override
    public List<ProductoDTO> findByCategoria(Long categoriaId) {

        Categoria categoria = categoriaRepository.findById(categoriaId)
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada"));
        return productoRepository.findByCategoria(categoria).stream()
                .map(ProductoMapper::toDTO)
                .toList();
    }

}
