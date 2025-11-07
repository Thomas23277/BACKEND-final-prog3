package com.foodstore.htmeleros.service;

import java.util.List;

import com.foodstore.htmeleros.entity.DetallePedido;


public interface DetallePedidoService {
    DetallePedido save(DetallePedido detalle);
    List<DetallePedido> findAll();
    DetallePedido findById(Long id);
    void deleteById(Long id);
    DetallePedido update(Long id, DetallePedido nuevo);
}
