package com.foodstore.htmeleros.mappers;

import com.foodstore.htmeleros.dto.ProductoDTO;
import com.foodstore.htmeleros.entity.Producto;


public class ProductoMapper {

    public static ProductoDTO toDTO(Producto producto) {
        if (producto == null) return null;

        ProductoDTO dto = new ProductoDTO();
        dto.setId(producto.getId());
        dto.setNombre(producto.getNombre());
        dto.setPrecio(producto.getPrecio());
        dto.setStock(producto.getStock()); // ✅ mapear stock
        if (producto.getCategoria() != null) {
            dto.setCategoria(CategoriaMapper.toDTO(producto.getCategoria()));
        }
        return dto;
    }

    public static Producto toEntity(ProductoDTO dto) {
        if (dto == null) return null;

        Producto producto = new Producto();
        producto.setId(dto.getId());
        producto.setNombre(dto.getNombre());
        producto.setPrecio(dto.getPrecio());
        producto.setStock(dto.getStock()); // ✅ mapear stock
        if (dto.getCategoria() != null) {
            producto.setCategoria(CategoriaMapper.toEntity(dto.getCategoria()));
        }
        return producto;
    }
}


