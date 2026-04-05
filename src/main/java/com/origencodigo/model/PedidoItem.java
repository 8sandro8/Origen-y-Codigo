package com.origencodigo.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoItem {
    private int id;
    private int pedidoId;
    private int productoId;
    private int cantidad;
    private BigDecimal precioUnitario;
}
