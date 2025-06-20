package com.example.api_ventas_inventario.models;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class AlertaStock {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long idPerfume;
    private String mensaje;
    private LocalDateTime fecha;
    private String tipo;  // fijo: "stock_bajo"
}
