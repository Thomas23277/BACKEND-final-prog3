package com.foodstore.htmeleros.service;

import com.foodstore.htmeleros.dto.CategoriaDTO;
import com.foodstore.htmeleros.entity.Categoria;
import com.foodstore.htmeleros.exception.ResourceNotFoundException;
import com.foodstore.htmeleros.mappers.CategoriaMapper;
import com.foodstore.htmeleros.repository.CategoriaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaServiceImpl implements CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaServiceImpl(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }
    public CategoriaDTO save(CategoriaDTO dto) {
        Categoria categoria = CategoriaMapper.toEntity(dto);
        Categoria c = categoriaRepository.save(categoria);
        return CategoriaMapper.toDTO(c);
    }
    @Override
    public List<CategoriaDTO> findAll() {
        return categoriaRepository.findAll().stream()
                .map(CategoriaMapper::toDTO)
                .toList();
    }
    @Override
    public CategoriaDTO findById(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria con id:" + id + " no encontrado"));
        return CategoriaMapper.toDTO(categoria);
    }
    @Override
    public void deleteById(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria con id:" + id + " no encontrado"));
        categoriaRepository.delete(categoria);
    }
    @Override
    public CategoriaDTO update(Long id, CategoriaDTO dto) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria con id:" + id + " no encontrada"));
        categoria.setNombre(dto.getNombre());

        Categoria actualizada = categoriaRepository.save(categoria);
        return CategoriaMapper.toDTO(actualizada);
    }

}