package com.foodstore.htmeleros.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.foodstore.htmeleros.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {}