package com.foodstore.htmeleros.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DetallePedidoDTO {
    private Long id;
    private Integer cantidad;
    private Double subtotal;
    private Double precioUnitario;
    private ProductoDTO productoDTO;

}
