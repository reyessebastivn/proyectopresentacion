package com.example.api_perfume.controllers;

import com.example.api_perfume.models.CompraRequest;
import com.example.api_perfume.models.CompraResponse;
import com.example.api_perfume.models.Perfume;
import com.example.api_perfume.models.User;
import com.example.api_perfume.services.PerfumeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
@RequestMapping("/v2/compra")
@Tag(name = "Compra API v2", description = "API versión 2 para la gestión de compras de perfumes")
public class CompraControllerV2 {

    @Autowired
    private PerfumeService perfumeService;

    @Autowired
    private WebClient webClient;

    @PostMapping("/comprar")
    @Operation(
        summary = "Realizar una compra de perfume",
        description = "Permite a un usuario comprar un perfume específico, validando stock, usuario y registrando la compra en el microservicio de ventas"
    )
    public CompraResponse comprar(
            @Parameter(description = "Datos necesarios para realizar la compra: ID de perfume e ID de usuario")
            @Valid @RequestBody CompraRequest compraRequest) {

        CompraResponse response = new CompraResponse();

        try {
            Long idPerfume = Long.parseLong(compraRequest.getIdPerfume());
            Perfume perfume = perfumeService.obtenerUno(idPerfume);
            if (perfume == null) throw new Exception("Perfume no encontrado");

            if (perfume.getStock() <= 0) throw new Exception("No hay stock disponible");

            User usuario = webClient
                    .get()
                    .uri("http://localhost:8081/usuario/" + compraRequest.getIdUsuario())
                    .retrieve()
                    .bodyToMono(User.class)
                    .block();

            if (usuario == null) throw new Exception("Usuario no encontrado");

            perfume.setStock(perfume.getStock() - 1);
            perfumeService.agregar(perfume);

            webClient.post()
                    .uri("http://localhost:8084/compras")
                    .bodyValue(compraRequest)
                    .retrieve()
                    .bodyToMono(Void.class)
                    .block();

            response.setIdBoleta("Compra exitosa. Perfume ID: " + perfume.getId() + " Correo usuario: " + usuario.getEmail());
            response.setStockRestante(perfume.getStock());
            response.setExito(true);

        } catch (Exception e) {
            response.setExito(false);
            response.setMensaje("Error al procesar la compra: " + e.getMessage());
        }

        return response;
    }
}
