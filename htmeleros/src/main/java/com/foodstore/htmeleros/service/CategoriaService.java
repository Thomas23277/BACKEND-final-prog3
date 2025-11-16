package com.foodstore.htmeleros.service;

import java.util.List;

import com.foodstore.htmeleros.dto.CategoriaDTO;
import com.foodstore.htmeleros.entity.Categoria;

public interface CategoriaService {
   CategoriaDTO save(CategoriaDTO categoria);
   CategoriaDTO findById(Long id);
    void deleteById(Long id);
    CategoriaDTO update(Long id,CategoriaDTO categoria);
    List<CategoriaDTO> findAll();
}
