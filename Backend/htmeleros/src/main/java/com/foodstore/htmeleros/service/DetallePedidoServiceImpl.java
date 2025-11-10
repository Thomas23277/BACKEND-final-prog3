package com.foodstore.htmeleros.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foodstore.htmeleros.entity.DetallePedido;
import com.foodstore.htmeleros.repository.DetallePedidoRepository;


@Service
public class DetallePedidoServiceImpl implements DetallePedidoService {

    @Autowired
    private DetallePedidoRepository repository;

    @Override
    public DetallePedido save(DetallePedido detalle) {
        detalle.setSubtotal(detalle.getCantidad() * detalle.getPrecioUnitario());
        return repository.save(detalle);
    }

    @Override
    public List<DetallePedido> findAll() {
        return repository.findAll();
    }

    @Override
    public DetallePedido findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("DetallePedido no encontrado"));
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public DetallePedido update(Long id, DetallePedido nuevo) {
        DetallePedido actual = findById(id);
        actual.setCantidad(nuevo.getCantidad());
        actual.setPrecioUnitario(nuevo.getPrecioUnitario());
        actual.setSubtotal(nuevo.getCantidad() * nuevo.getPrecioUnitario());
        return repository.save(actual);
    }
}

