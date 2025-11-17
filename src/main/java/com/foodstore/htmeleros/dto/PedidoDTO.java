package com.foodstore.htmeleros.dto;

import com.foodstore.htmeleros.entity.DetallePedido;
import com.foodstore.htmeleros.enums.Estado;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PedidoDTO {
    private Long id;
    private LocalDateTime fecha;
    private Estado estado;
    private double total;
    private List<DetallePedidoDTO> detalles;
    private UsuarioDTO usuarioDTO;
}
