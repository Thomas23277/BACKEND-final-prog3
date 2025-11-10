package com.foodstore.htmeleros.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.foodstore.htmeleros.entity.Categoria;
import com.foodstore.htmeleros.exception.ResourceNotFoundException;
import com.foodstore.htmeleros.repository.CategoriaRepository;


@Service
public class CategoriaServiceImpl implements CategoriaService {
    
    private final CategoriaRepository categoriaRepository;

    public CategoriaServiceImpl(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }
    
    @Override
    public Categoria save(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    @Override
    public List<Categoria> findAll() {
        return categoriaRepository.findAll();
    }

    @Override
        public Categoria findById(Long id) {
            Categoria categoria= categoriaRepository.findById(id).orElseThrow(
                 ()->{
                    throw new ResourceNotFoundException("Categoria con id:"+id+" no encontrado");
                 }
            );
       
        return categoria;
    }
    
    @Override
    public void deleteById(Long id) {
        categoriaRepository.deleteById(id);
    }

    

    @Override
    public Categoria update(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }
}
