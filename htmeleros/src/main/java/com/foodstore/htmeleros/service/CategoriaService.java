package com.foodstore.htmeleros.service;

import java.util.List;

import com.foodstore.htmeleros.entity.Categoria;

public interface CategoriaService {
   Categoria save(Categoria categoria);
   List<Categoria> findAll();
   Categoria findById(Long id);
    void deleteById(Long id);
    Categoria update(Categoria categoria);
}
