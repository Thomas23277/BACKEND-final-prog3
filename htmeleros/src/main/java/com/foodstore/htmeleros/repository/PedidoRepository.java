package com.foodstore.htmeleros.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.foodstore.htmeleros.entity.Pedido;


public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    
}