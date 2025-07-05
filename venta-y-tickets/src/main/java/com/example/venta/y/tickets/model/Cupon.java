package com.example.venta.y.tickets.model;

import java.sql.Date;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Entity
@Table(name = "cupones")
public class Cupon {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idCupon;

    @Column(name = "codigo", nullable = false, unique = true)
    @NotBlank
    private String codigo;

    @Column(name = "descuento", nullable = false)
    private double descuento;

    @Column(name = "activo", nullable = false)
    private boolean activo;

    @Column(name = "fecha_expiracion")
    private Date fechaExpiracion;


}
