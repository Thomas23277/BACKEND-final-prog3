package com.foodstore.htmeleros.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foodstore.htmeleros.entity.Usuario;
import com.foodstore.htmeleros.repository.UsuarioRepository;


@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuariorepository;

    @Override
    public Usuario save(Usuario usuario) {
        return usuariorepository.save(usuario);
    }

    @Override
    public List<Usuario> findAll() {
        return usuariorepository.findAll();
    }

    @Override
    public Usuario findById(Long id) {
        return usuariorepository.findById(id)
                .orElse(null);
    }

    @Override
    public void deleteById(Long id) {
        usuariorepository.deleteById(id);
    }

    @Override
    public Usuario update(Long id, Usuario nuevo) {
        Usuario actual = findById(id);
        actual.setNombre(nuevo.getNombre());
        actual.setEmail(nuevo.getEmail());
        return usuariorepository.save(actual);
    }
    @Override
    public Usuario findByEmail(String email) {
        
        return usuariorepository.findByEmail(email).orElse(null);
    }
}

