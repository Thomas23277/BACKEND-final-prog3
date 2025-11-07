package com.foodstore.htmeleros.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foodstore.htmeleros.entity.Usuario;
import com.foodstore.htmeleros.repository.UsuarioRepository;


@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    @Override
    public Usuario save(Usuario usuario) {
        return repository.save(usuario);
    }

    @Override
    public List<Usuario> findAll() {
        return repository.findAll();
    }

    @Override
    public Usuario findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Usuario update(Long id, Usuario nuevo) {
        Usuario actual = findById(id);
        actual.setNombre(nuevo.getNombre());
        actual.setEmail(nuevo.getEmail());
        return repository.save(actual);
    }
}

