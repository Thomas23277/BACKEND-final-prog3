package com.foodstore.htmeleros.service;

import java.util.List;

import com.foodstore.htmeleros.entity.Producto;

public interface ProductoService {
   Producto save(Producto producto);
   List<Producto> findAll();
   Producto findById(Long id);
   void deleteById(Long id);
   Producto update(Producto producto);
}
