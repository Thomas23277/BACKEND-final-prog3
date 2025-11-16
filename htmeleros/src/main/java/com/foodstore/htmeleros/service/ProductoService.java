package com.foodstore.htmeleros.service;

import com.foodstore.htmeleros.dto.ProductoDTO;
import java.util.List;

public interface ProductoService {
    ProductoDTO save(ProductoDTO dto);
    List<ProductoDTO> findAll();
    ProductoDTO findById(Long id);
    void deleteById(Long id);
    ProductoDTO update(ProductoDTO dto);
    ProductoDTO venderProducto(Long productoId, int cantidad);
    ProductoDTO agregarStock(Long productoId, int cantidad);
    List<ProductoDTO> findByCategoria(Long categoriaId);

}
