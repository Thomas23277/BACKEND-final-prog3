package com.foodstore.htmeleros.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.foodstore.htmeleros.entity.DetallePedido;
import com.foodstore.htmeleros.entity.Pedido;
import com.foodstore.htmeleros.repository.PedidoRepository;


@Service
public class PedidoServiceImpl implements PedidoService {

    @Autowired
    private PedidoRepository repository;

    @Autowired
    private ProductoService productoService;

    @Override
    @Transactional
    public Pedido save(Pedido pedido) {
        pedido.setFecha(LocalDateTime.now());

        // Ensure detalles link to pedido and compute subtotales, update stock
        if (pedido.getDetalles() != null) {
            for (DetallePedido det : pedido.getDetalles()) {
                if (det.getProducto() == null || det.getProducto().getId() == null) {
                    throw new IllegalArgumentException("Cada detalle debe contener el producto con su id");
                }
                // set precioUnitario if not provided from producto (optional)
                if (det.getPrecioUnitario() == null) {
                    det.setPrecioUnitario(productoService.findById(det.getProducto().getId()).getPrecio());
                }
                det.setSubtotal(det.getCantidad() * det.getPrecioUnitario());
                det.setPedido(pedido);

                // Reducir stock en el producto
                productoService.venderProducto(det.getProducto().getId(), det.getCantidad());
            }
        }

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

