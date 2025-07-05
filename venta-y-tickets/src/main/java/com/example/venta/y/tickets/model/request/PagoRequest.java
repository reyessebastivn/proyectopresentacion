package com.example.venta.y.tickets.model.request;

import lombok.Data;

@Data
public class PagoRequest {
    private Long idUsuario;
    private Long idPerfume;
    private int precio;
    private String codigoCupon;
}
