package com.example.api_ventas_inventario.models.request;

import lombok.Data;

@Data
public class CompraRequest {
    
    private Long idUsuario;
    private Long idPerfume;
    private int cantidad;

}
