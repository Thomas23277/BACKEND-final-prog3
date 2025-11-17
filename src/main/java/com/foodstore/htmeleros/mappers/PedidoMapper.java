package com.foodstore.htmeleros.mappers;

import com.foodstore.htmeleros.dto.PedidoDTO;
import com.foodstore.htmeleros.entity.Pedido;

public class PedidoMapper {

    public static PedidoDTO toDTO(Pedido p) {
        if (p == null) return null;

        PedidoDTO dto = new PedidoDTO();
        dto.setId(p.getId());
        dto.setFecha(p.getFecha());
        dto.setEstado(p.getEstado());
        dto.setTotal(p.getTotal());

        if (p.getUsuario() != null) {
            dto.setUsuarioDTO(UsuarioMapper.toDTO(p.getUsuario()));
        }

        if (p.getDetalles() != null) {
            dto.setDetalles(
                    p.getDetalles().stream()
                            .map(DetallePedidoMapper::toDTO)
                            .toList()
            );
        }
        return dto;
    }

    public static Pedido toEntity(PedidoDTO dto) {
        if (dto == null) return null;

        Pedido p = new Pedido();
        p.setId(dto.getId());
        p.setFecha(dto.getFecha());
        p.setEstado(dto.getEstado());
        p.setTotal(dto.getTotal());

        if (dto.getUsuarioDTO() != null) {
            p.setUsuario(UsuarioMapper.toEntity(dto.getUsuarioDTO()));
        }

        if (dto.getDetalles() != null) {
            p.setDetalles(
                    dto.getDetalles().stream()
                            .map(DetallePedidoMapper::toEntity)
                            .peek(det -> det.setPedido(p)) // asegurar la relación bidireccional
                            .toList()
            );
        }
        return p;
    }
}

