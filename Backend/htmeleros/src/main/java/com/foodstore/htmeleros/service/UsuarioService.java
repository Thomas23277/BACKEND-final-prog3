package com.foodstore.htmeleros.service;

import java.util.List;

import com.foodstore.htmeleros.entity.Usuario;

public interface UsuarioService {
    Usuario save(Usuario usuario);
    List<Usuario> findAll();
    Usuario findById(Long id);
    void deleteById(Long id);
    Usuario update(Long id, Usuario nuevo);
    Usuario findByEmail(String email);
}
