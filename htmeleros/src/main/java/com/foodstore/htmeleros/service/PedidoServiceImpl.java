package com.foodstore.htmeleros.service;

import java.time.LocalDateTime;
import java.util.List;

import com.foodstore.htmeleros.mappers.PedidoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.foodstore.htmeleros.dto.PedidoDTO;
import com.foodstore.htmeleros.entity.DetallePedido;
import com.foodstore.htmeleros.entity.Pedido;
import com.foodstore.htmeleros.exception.ResourceNotFoundException;
import com.foodstore.htmeleros.repository.PedidoRepository;

@Service
public class PedidoServiceImpl implements PedidoService {

    @Autowired
    private PedidoRepository repository;

    @Autowired
    private ProductoService productoService;

    @Override
    @Transactional
    public PedidoDTO save(PedidoDTO dto) {
        Pedido pedido = PedidoMapper.toEntity(dto);
        pedido.setFecha(LocalDateTime.now());

        // ✅ Validar stock ANTES de guardar
        validarStockDetalles(pedido.getDetalles());

        // Procesar detalles
        if (pedido.getDetalles() != null) {
            for (DetallePedido det : pedido.getDetalles()) {
                if (det.getProducto() == null || det.getProducto().getId() == null) {
                    throw new IllegalArgumentException("Cada detalle debe contener el producto con su id");
                }
                if (det.getPrecioUnitario() == null) {
                    det.setPrecioUnitario(productoService.findById(det.getProducto().getId()).getPrecio());
                }
                det.setSubtotal(det.getCantidad() * det.getPrecioUnitario());
                det.setPedido(pedido);

                // Reducir stock
                productoService.venderProducto(det.getProducto().getId(), det.getCantidad());
            }
        }

        Pedido guardado = repository.save(pedido);
        return PedidoMapper.toDTO(guardado);
    }

    private void validarStockDetalles(List<DetallePedido> detalles) {
        if (detalles == null || detalles.isEmpty()) {
            return;
        }

        for (DetallePedido det : detalles) {
            if (det.getProducto() == null || det.getProducto().getId() == null) {
                throw new IllegalArgumentException("Cada detalle debe contener el producto con su id");
            }

            Long productoId = det.getProducto().getId();
            int cantidadSolicitada = det.getCantidad() != null ? det.getCantidad() : 1;

            var producto = productoService.findById(productoId);

            if (producto.getStock() == 0) {
                throw new IllegalStateException(
                        String.format("El producto '%s' no tiene stock disponible", producto.getNombre())
                );
            }

            if (cantidadSolicitada > producto.getStock()) {
                throw new IllegalArgumentException(
                        String.format(
                                "Stock insuficiente para '%s'. Solicitaste %d pero solo hay %d disponibles",
                                producto.getNombre(), cantidadSolicitada, producto.getStock()
                        )
                );
            }
        }
    }

    @Override
    public List<PedidoDTO> findAll() {
        return repository.findAll().stream()
                .map(PedidoMapper::toDTO)
                .toList();
    }

    @Override
    public PedidoDTO findById(Long id) {
        Pedido pedido = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido no encontrado"));
        return PedidoMapper.toDTO(pedido);
    }

    @Override
    public void deleteById(Long id) {
        Pedido pedido = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido no encontrado"));
        repository.delete(pedido);
    }

    @Override
    public PedidoDTO update(Long id, PedidoDTO nuevo) {
        Pedido actual = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido no encontrado"));

        actual.setUsuario(PedidoMapper.toEntity(nuevo).getUsuario());
        // suponiendo que usuario también es DTO
        actual.setFecha(nuevo.getFecha());

        Pedido actualizado = repository.save(actual);
        return PedidoMapper.toDTO(actualizado);
    }
}
