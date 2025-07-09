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
        this.webClientUsuarios = webClientBuilder.baseUrl("http://localhost:8083/usuario").build();
        this.webClientPerfumes = webClientBuilder.baseUrl("http://localhost:8083/api/perfumes").build();

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

    if (perfume == null) {
        throw new RuntimeException("Perfume no encontrado");
    }

    // Verificar stock
    if (perfume.getStock() < compra.getCantidad()) {
        throw new RuntimeException("Stock insuficiente para completar la compra.");
    }

    // Calcular total
    double totalCalculado = perfume.getPrecio() * compra.getCantidad();
    compra.setTotal(totalCalculado);

    // Guardar compra
    compraRepository.save(compra);

    // Descontar stock remotamente usando el endpoint /descontarStock/{id}/{cantidad}
    webClientPerfumes.put()
            .uri("/descontarStock/{id}/{cantidad}", compra.getPerfumeId(), compra.getCantidad())
            .retrieve()
            .bodyToMono(Void.class)
            .block();

    // Preparar respuesta
    CompraResponse response = new CompraResponse();
    response.setCompraId(compra.getId());
    response.setNombreUsuario(usuario != null ? usuario.getNombre() : "Desconocido");
    response.setNombrePerfume(perfume.getMarca());
    response.setCantidad(compra.getCantidad());
    response.setTotal(compra.getTotal());
    response.setFecha(compra.getFecha());

    return response;
}




    public List<Compra> obtenerComprasPorUsuario(Long usuarioId) {
        return compraRepository.findByUsuarioId(usuarioId);
    }

}
