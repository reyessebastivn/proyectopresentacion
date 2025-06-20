package com.example.api_perfume.models.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "sucursales")
@Data
public class Sucursal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String direccion;
    private String horarioApertura; // Ej: "09:00"
    private String horarioCierre;   // Ej: "18:00"

    @Column(columnDefinition = "TEXT")
    private String personalAsignado; // Lista de nombres, o más adelante relación con tabla de empleados

    @Column(columnDefinition = "TEXT")
    private String politicasLocales;



    
}
