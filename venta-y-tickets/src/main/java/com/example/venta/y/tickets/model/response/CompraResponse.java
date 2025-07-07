package com.example.venta.y.tickets.model.response;

import java.time.LocalDate;

import lombok.Data;
@Data
public class CompraResponse {
    private Long compraId;
    private String nombreUsuario;
    private String nombrePerfume;
    private Integer cantidad;
    private Double total;
    private LocalDate fecha;

    
}

