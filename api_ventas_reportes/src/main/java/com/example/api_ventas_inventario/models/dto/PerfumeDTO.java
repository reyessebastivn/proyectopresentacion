package com.example.api_ventas_inventario.models.dto;

import lombok.Data;

@Data
public class PerfumeDTO {
    private Long id;
    private String nombre;
    private int stock;
    private int precio;
}
