package com.foodstore.htmeleros.mappers;

import com.foodstore.htmeleros.dto.DetallePedidoDTO;
import com.foodstore.htmeleros.entity.DetallePedido;

public class DetallePedidoMapper {

    public static DetallePedidoDTO toDTO(DetallePedido detalle) {
        if (detalle == null) {
            return null;
        }

        DetallePedidoDTO dto = new DetallePedidoDTO();
        dto.setId(detalle.getId());
        dto.setCantidad(detalle.getCantidad());
        dto.setPrecioUnitario(detalle.getPrecioUnitario());
        dto.setSubtotal(detalle.getSubtotal());

        // si tiene relación con producto, mapearlo también
        if (detalle.getProducto() != null) {
            dto.setProductoDTO(ProductoMapper.toDTO(detalle.getProducto()));
        }

        return dto;
    }

    public static DetallePedido toEntity(DetallePedidoDTO dto) {
        if (dto == null) {
            return null;
        }

        DetallePedido detalle = new DetallePedido();
        detalle.setId(dto.getId());
        detalle.setCantidad(dto.getCantidad());
        detalle.setPrecioUnitario(dto.getPrecioUnitario());
        detalle.setSubtotal(dto.getSubtotal());

        // si el DTO trae producto, mapearlo también
        if (dto.getProductoDTO() != null) {
            detalle.setProducto(ProductoMapper.toEntity(dto.getProductoDTO()));
        }

        return detalle;
    }
}


