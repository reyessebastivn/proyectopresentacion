package com.example.api_ventas_inventario.models;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Boleta {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String numeroBoleta;
    private Double MontoTotal;
    private LocalDateTime fecha;

    private Long idCompra; // Asociación a la compra generada
}
