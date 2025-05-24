package com.example.api_perfume.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.api_perfume.models.CompraRequest;
import com.example.api_perfume.models.CompraResponse;
import com.example.api_perfume.models.Perfume;
import com.example.api_perfume.models.User;
import com.example.api_perfume.services.PerfumeService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("compra")
public class CompraController {

    @Autowired
    private PerfumeService perfumeService;

    @Autowired
    private WebClient webClient;

    @PostMapping("/comprar")
    public CompraResponse comprar(@Valid @RequestBody CompraRequest compraRequest) {
        
        CompraResponse response = new CompraResponse();
    
        try { 
            Long idPerfume = Long.parseLong(compraRequest.getIdPerfume());
            Perfume Per = perfumeService.obtenerUno(idPerfume);
            if (Per == null) throw new Exception("Perfume no encontrado");

            // Validar stock
            if (Per.getStock() <= 0) {
                throw new Exception("No hay stock disponible");
            }

            // Obtener usuario desde microservicio usuarios
            User usuario = webClient
                .get()
                .uri("http://localhost:8081/usuario/" + compraRequest.getIdUsuario())
                .retrieve()
                .bodyToMono(User.class)
                .block();

            if (usuario == null) throw new Exception("Usuario no encontrado");

            // Restar stock y guardar
            Per.setStock(Per.getStock() - 1);
            perfumeService.agregar(Per); // actualiza el stock

            webClient.post()
            .uri("http://localhost:8084/compras")
            .bodyValue(compraRequest) // puedes enviar CompraRequest si el microservicio acepta esos campos
            .retrieve()
            .bodyToMono(Void.class)  // si el microservicio devuelve algo puedes usar Compra.class
            .block();
           

            // Armar respuesta
            response.setIdBoleta("Compra exitosa. Perfume ID: " + Per.getId() + " Correo usuario: " + usuario.getEmail());
            response.setStockRestante(Per.getStock());
            response.setExito(true);

        } catch (Exception e) {
            response.setExito(false);
            response.setMensaje("Error al procesar la compra: " + e.getMessage());
        }

        return response;
    }
}