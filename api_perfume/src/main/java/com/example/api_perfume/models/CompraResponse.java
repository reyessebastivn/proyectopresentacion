package com.example.api_perfume.models;

import lombok.Data;

@Data
public class CompraResponse {
    private String idBoleta;
    private String mensaje;
    private Boolean exito;

    private Integer stockRestante;
}
