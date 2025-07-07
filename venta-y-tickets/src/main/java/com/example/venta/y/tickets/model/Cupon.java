package com.example.venta.y.tickets.model;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Entity
@Table(name = "cupones")
public class Cupon {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private long idCupon;

    @Column(name = "codigo", nullable = false, unique = true)
    @NotBlank(message = "El código no puede estar vacío")
    @Size(max = 50, message = "El código no debe superar los 50 caracteres")
    private String codigo;

    @Column(name = "descuento", nullable = false)
    @Min(0)/*Descuento en porcentaje */
    @Max(100)
    private double descuento;

    @Column(name = "activo", nullable = false)
    private boolean activo;

    @Column(name = "fecha_expiracion")
    @FutureOrPresent(message = "La fecha de expiración debe ser futura o actual")
    private Date fechaExpiracion;
}
