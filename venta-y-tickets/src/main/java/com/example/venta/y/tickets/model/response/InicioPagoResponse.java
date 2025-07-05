package com.example.venta.y.tickets.model.response;

import lombok.Data;

@Data
public class InicioPagoResponse {
    private boolean exito;
    private String urlPago;
    private String mensaje;
}