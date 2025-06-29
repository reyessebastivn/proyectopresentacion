package com.example.models.requests;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserUpdate {

    @NotNull(message = "El id del usuario es obligatorio para actualizar")
    private Integer id;
    
    private String nombre;

    private String password;

    private String telefono;
}
