package com.foodstore.htmeleros.service;

import com.foodstore.htmeleros.dto.PedidoDTO;
import java.util.List;

public interface PedidoService {
    PedidoDTO save(PedidoDTO pedido);
    List<PedidoDTO> findAll();
    PedidoDTO findById(Long id);
    void deleteById(Long id);
    PedidoDTO update(Long id, PedidoDTO nuevo);
}
