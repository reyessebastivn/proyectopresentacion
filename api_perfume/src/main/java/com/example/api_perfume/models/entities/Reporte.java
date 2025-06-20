package com.example.api_perfume.models.entities;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "reportes")
@Data

public class Reporte {

     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tipo; // "ventas", "inventario", "rendimiento"
    private String sucursal; // nombre o código de sucursal
    private LocalDate fechaGeneracion;

    @Column(columnDefinition = "TEXT")
    private String contenido; // Reporte en texto o resumen de datos
    
}
