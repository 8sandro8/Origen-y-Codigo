package com.origencodigo.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
    private int id;
    private String nombre;
    private String email;
    private String contrasena;
    private LocalDate fechaRegistro;
    private boolean esAdmin;
}
