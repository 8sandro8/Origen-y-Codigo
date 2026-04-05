package com.origencodigo.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pedido {
    private int id;
    private int usuarioId;
    private LocalDateTime fechaPedido;
    private BigDecimal total;
    private String estado;
    
    public String getFechaFormateada() {
        if (fechaPedido == null) return "";
        return fechaPedido.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }
}
