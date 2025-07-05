package com.example.api_perfume.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.Map;

@RestController
@RequestMapping("/v2/reportes")
@Tag(name = "Reportes API v2", description = "API versión 2 para la visualizacion de reportes")
public class ReporteControllerV2 {

    @Autowired
    private WebClient.Builder webClientBuilder;

    // Puedes mover esto a application.properties y usar @Value
    private static final String BASE_URL_VENTAS = "http://microservicio01/usuario";

    @GetMapping("/clientes")
    public ResponseEntity<Integer> cantidadClientesPorSucursalYFecha(
            @RequestParam String fechaInicio,
            @RequestParam String fechaFin,
            @RequestParam Long sucursalId) {

        try {
            Integer cantidad = webClientBuilder.build()
                    .get()
                    .uri(BASE_URL_VENTAS + "/cantidad-clientes?sucursalId={sid}&fechaInicio={fi}&fechaFin={ff}",
                            sucursalId, fechaInicio, fechaFin)
                    .retrieve()
                    .bodyToMono(Integer.class)
                    .block();

            return ResponseEntity.ok(cantidad != null ? cantidad : 0);

        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(0);
        }
    }

    @GetMapping("/perfume-mas-vendido")
    public ResponseEntity<Map<String, Object>> perfumeMasVendido(
            @RequestParam String fechaInicio,
            @RequestParam String fechaFin,
            @RequestParam Long sucursalId) {

        try {
            Map<String, Object> data = webClientBuilder.build()
                    .get()
                    .uri(BASE_URL_VENTAS + "/perfume-mas-vendido?sucursalId={sid}&fechaInicio={fi}&fechaFin={ff}",
                            sucursalId, fechaInicio, fechaFin)
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();

            return ResponseEntity.ok(data);

        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(null);
        }
    }

    @GetMapping("/ganancias")
    public ResponseEntity<Double> gananciasPorSucursal(
            @RequestParam String fechaInicio,
            @RequestParam String fechaFin,
            @RequestParam Long sucursalId) {

        try {
            Double ganancias = webClientBuilder.build()
                    .get()
                    .uri(BASE_URL_VENTAS + "/ganancias?sucursalId={sid}&fechaInicio={fi}&fechaFin={ff}",
                            sucursalId, fechaInicio, fechaFin)
                    .retrieve()
                    .bodyToMono(Double.class)
                    .block();

            return ResponseEntity.ok(ganancias != null ? ganancias : 0.0);

        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(0.0);
        }
    }
}


