package com.example.api_perfume.models.entities;


import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ventas")  // Nombre de la tabla en la base de datos
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // ID autoincremental
    private Long id;

    private String nombreUsuario;  // Nombre del cliente que realizó la compra

    private String rutUsuario;     // RUT del cliente

    private String sucursal;       // Sucursal donde se realizó la venta

    private String nombrePerfume;  // Nombre del perfume vendido

    private int cantidad;          // Cantidad de perfumes comprados

    private double total;          // Total pagado (cantidad * precio unitario)

    private LocalDateTime fechaCompra;  // Fecha y hora de la compra

    // Se ejecuta automáticamente antes de guardar (asigna fecha actual)
    @PrePersist
    public void prePersist() {
        this.fechaCompra = LocalDateTime.now();
    }
}
