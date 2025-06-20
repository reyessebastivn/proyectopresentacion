package com.example.api_ventas_inventario.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.api_ventas_inventario.models.Perfume;

public class InventarioController {
        @Autowired
    private WebClient.Builder webClientBuilder;

    @GetMapping("/{idPerfume}")
    public Perfume consultarStock(@PathVariable Long idPerfume) {
        // Llamar al microservicio de perfumes
        Perfume perfume = webClientBuilder.build()
            .get()
            .uri("http://localhost:8082/api/perfumes/" + idPerfume)
            .retrieve()
            .bodyToMono(Perfume.class)
            .block();

        if (perfume == null) {
            throw new RuntimeException("Perfume no encontrado");
        }

        return perfume;
    }
}
