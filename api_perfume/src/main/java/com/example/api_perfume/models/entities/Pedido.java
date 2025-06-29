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
@Table(name = "pedidos")
@Data
public class Pedido {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sucursal;
    private LocalDate fechaPedido;

    @Column(columnDefinition = "TEXT")
    private String productosSolicitados; 

    private String estado; 

    private String observaciones;
    
}
