package com.example.venta.y.tickets.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
public class Devolucion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El id de venta es obligatorio")
    private Long idVenta;

    @NotBlank(message = "El motivo no puede estar vacío")
    @Size(max = 255, message = "El motivo no puede superar los 255 caracteres")
    private String motivo;

    private LocalDateTime fecha;

    @NotBlank(message = "El estado no puede estar vacío")
    private String estado;
}
