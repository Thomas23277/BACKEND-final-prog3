package com.foodstore.htmeleros.service;

import java.util.List;

import com.foodstore.htmeleros.entity.Pedido;

public interface PedidoService {
    Pedido save(Pedido pedido);
    List<Pedido> findAll();
    Pedido findById(Long id);
    void deleteById(Long id);
    Pedido update(Long id, Pedido nuevo);
}
