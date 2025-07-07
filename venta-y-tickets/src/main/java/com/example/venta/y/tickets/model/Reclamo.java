package com.example.venta.y.tickets.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
public class Reclamo {

  
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @NotBlank(message = "El nombre del cliente es obligatorio")
    @Size(max = 50, message = "El nombre del cliente no puede superar los 50 caracteres")
    private String clienteNombre;

    @NotBlank(message = "La descripción no puede estar vacía")
    @Size(max = 250, message = "La descripción no puede superar los 250 caracteres")
    private String descripcion;

    @NotBlank(message = "El estado es obligatorio")
    @Size(max = 20, message = "El estado no puede superar los 20 caracteres")
    private String estado;

    private LocalDateTime fecha;
}