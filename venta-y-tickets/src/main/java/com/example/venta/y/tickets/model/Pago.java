package com.example.venta.y.tickets.model;

import java.time.LocalDate;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "pago")
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pago")
    private Long idPago;

    @Column(name = "id_usuario", nullable = false)
    private Long idUsuario;

    @Column(name = "id_perfume", nullable = false)
    private Long idPerfume; 

    @Column(name = "precio", nullable = false)
    private int precio;

    @Column(name = "fecha_pago", nullable = false)
    private LocalDate fechaPago;

    @Column(name = "codigo_cupon")
    private String codigoCupon; 

    @Column(name = "id_transaccion_transbank", length = 100)
    private String idTransBank; 
}