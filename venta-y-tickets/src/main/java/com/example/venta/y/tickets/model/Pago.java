package com.example.venta.y.tickets.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Table(name = "pago")
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(name = "id_pago")
    private Long idPago;

    @NotNull(message = "El id de usuario es obligatorio")
    @Column(name = "id_usuario", nullable = false)
    private Long idUsuario;

    @NotNull(message = "El id de perfume es obligatorio")
    @Column(name = "id_perfume", nullable = false)
    private Long idPerfume;

    @Min(value = 0, message = "El precio no puede ser negativo")
    @Column(name = "precio", nullable = false)
    private int precio;

    @NotNull(message = "La fecha de pago es obligatoria")
    @PastOrPresent(message = "La fecha de pago no puede ser futura")
    @Column(name = "fecha_pago", nullable = false)
    private LocalDate fechaPago;

    @Size(max = 50, message = "El código de cupón no puede superar los 50 caracteres")
    @Column(name = "codigo_cupon")
    private String codigoCupon;

    @Size(max = 100, message = "El id de Transbank no puede superar los 100 caracteres")
    @Column(name = "id_transaccion_transbank", length = 100)
    private String idTransBank;

    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "La cantidad mínima es 1")
    @Column(name = "cantidad", nullable = false)
    private Integer cantidad;
}
