package com.origencodigo.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Categoria {
    private int id;
    private String nombre;
    private String descripcion;
}
