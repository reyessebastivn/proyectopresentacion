package com.example.api_ventas_inventario.models;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // ✅ ID correcto de la tabla

    private Long idCompra;

    private Long idPerfume;  // ✅ Relación con Perfume, sin auto_increment

    private int cantidad;

    @Column(nullable = false)
    private Double MontoTotal;  // ✅ nombre corregido

    private Long idUsuario;

    private LocalDateTime fechaVenta;
}
