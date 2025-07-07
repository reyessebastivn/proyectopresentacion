package com.example.venta.y.tickets.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.venta.y.tickets.model.Compra;
import com.example.venta.y.tickets.model.dto.PerfumeDTO;
import com.example.venta.y.tickets.model.dto.UserDTO;
import com.example.venta.y.tickets.model.response.CompraResponse;
import com.example.venta.y.tickets.repository.CompraRepository;

@Service
public class CompraService {

    private final CompraRepository compraRepository;
    private final WebClient webClientUsuarios;
    private final WebClient webClientPerfumes;

    @Autowired
    public CompraService(CompraRepository compraRepository, WebClient.Builder webClientBuilder) {
        this.compraRepository = compraRepository;
        this.webClientUsuarios = webClientBuilder.baseUrl("http://localhost:8081/api/usuarios").build();
        this.webClientPerfumes = webClientBuilder.baseUrl("http://localhost:8082/api/perfumes").build();
    }

    public CompraResponse registrarCompra(Compra compra) {
        UserDTO usuario = webClientUsuarios.get()
                .uri("/{id}", compra.getUsuarioId())
                .retrieve()
                .bodyToMono(UserDTO.class)
                .block();

        PerfumeDTO perfume = webClientPerfumes.get()
                .uri("/{id}", compra.getPerfumeId())
                .retrieve()
                .bodyToMono(PerfumeDTO.class)
                .block();

        CompraResponse response = new CompraResponse();
        response.setCompraId(compra.getId());
        response.setNombreUsuario(usuario != null ? usuario.getName() : "Desconocido");
        response.setNombrePerfume(perfume != null ? perfume.getNombre() : "Desconocido");
        response.setCantidad(compra.getCantidad());
        response.setTotal(compra.getTotal());
        response.setFecha(compra.getFecha());

        return response;
    }

    public List<Compra> obtenerComprasPorUsuario(Long usuarioId) {
        return compraRepository.findByUsuarioId(usuarioId);
    }

    
}
