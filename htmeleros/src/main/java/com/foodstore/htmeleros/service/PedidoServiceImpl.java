package com.foodstore.htmeleros.service;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.foodstore.htmeleros.entity.Pedido;
import com.foodstore.htmeleros.repository.PedidoRepository;


@Service
public class PedidoServiceImpl implements PedidoService {

    @Autowired
    private PedidoRepository repository;

    @Override
    public Pedido save(Pedido pedido) {
        pedido.setFecha(LocalDateTime.now());
        return repository.save(pedido);
    }

    @Override
    public List<Pedido> findAll() {
        return repository.findAll();
    }

    @Override
    public Pedido findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Pedido update(Long id, Pedido nuevo) {
        Pedido actual = findById(id);
        actual.setUsuario(nuevo.getUsuario());
        actual.setFecha(nuevo.getFecha());
        return repository.save(actual);
    }
}
