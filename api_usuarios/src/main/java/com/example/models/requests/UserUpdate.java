package com.example.models.requests;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserUpdate {

    @NotNull
    private Integer id;
    
    private String nombre;
    private String password;
    private String telefono;



}
