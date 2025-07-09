package com.example.venta.y.tickets.model.request;

import lombok.Data;

@Data
public class PagoRequest {
    private Long nombreUsuario;
    private Long nombrePerfume;
    private String codigoCupon;
}
