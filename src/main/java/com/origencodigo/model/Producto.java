package com.origencodigo.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Producto {
    private int id;
    private int categoriaId;
    private String nombre;
    private String descripcion;
    private BigDecimal precio;
    private String origen;
    private String imagenUrl;
    private boolean stockDisponible;
}
