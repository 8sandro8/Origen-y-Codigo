package com.origencodigo.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pedido {
    private int id;
    private int usuarioId;
    private LocalDateTime fechaPedido;
    private BigDecimal total;
    private String estado;
}
