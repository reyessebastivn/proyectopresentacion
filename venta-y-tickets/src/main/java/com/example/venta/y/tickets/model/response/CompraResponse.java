package com.example.venta.y.tickets.model.response;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
@Data
public class CompraResponse {
    private Long compraId;
    private String nombreUsuario;
    private String nombrePerfume;
    private Integer cantidad;


    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Double total;

    
    private LocalDate fecha;

    
}

