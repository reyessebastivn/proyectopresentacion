package com.example.api_ventas_inventario.services;

import com.example.api_ventas_inventario.models.*;
import com.example.api_ventas_inventario.models.dto.PerfumeDTO;
import com.example.api_ventas_inventario.models.request.CompraRequest;
import com.example.api_ventas_inventario.repositories.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CompraService {

    @Autowired
    private CompraRepository compraRepo;

    @Autowired
    private VentaRepository ventaRepo;

    @Autowired
    private BoletaRepository boletaRepo;

    @Autowired
    private AlertaStockRepository alertaRepo;

    private final WebClient webClient;

    public CompraService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8082").build(); // api_perfume
    }

    public Compra registrarCompra(CompraRequest req) {

        // 1. Obtener el perfume desde el microservicio api_perfume
        PerfumeDTO perfume = webClient.get()
                .uri("/api/perfumes/" + req.getIdPerfume())  // ✅ endpoint correcto
                .retrieve()
                .bodyToMono(PerfumeDTO.class)
                .block();

        if (perfume == null) {
            throw new RuntimeException("Perfume no encontrado");
        }

        if (perfume.getStock() < req.getCantidad()) {
            throw new RuntimeException("Stock insuficiente");
        }

        // 2. Crear compra
        Compra compra = new Compra();
        compra.setIdUsuario(req.getIdUsuario());
        compra.setIdPerfume(req.getIdPerfume());
        compra.setCantidad(req.getCantidad());
        compra.setFecha(LocalDateTime.now());
        compraRepo.save(compra);

        // 3. Crear venta asociada
        Venta venta = new Venta();
        venta.setIdCompra(compra.getId());
        venta.setCantidad(req.getCantidad());
        double monto = req.getCantidad() * perfume.getPrecio();
        venta.setMontoTotal(monto); // ✅ solo usamos este campo
        venta.setIdUsuario(req.getIdUsuario());
        venta.setIdPerfume(req.getIdPerfume());
        venta.setFechaVenta(LocalDateTime.now());
        ventaRepo.save(venta);

        // 4. Crear boleta
        Boleta boleta = new Boleta();
        boleta.setIdCompra(compra.getId());
        boleta.setFecha(LocalDateTime.now());
        boleta.setNumeroBoleta("BLT-" + compra.getId());
        boleta.setMontoTotal(venta.getMontoTotal());
        boletaRepo.save(boleta);

        // 5. Alerta si el stock es bajo
        if (perfume.getStock() - req.getCantidad() < 5) {
            AlertaStock alerta = new AlertaStock();
            alerta.setIdPerfume(req.getIdPerfume());
            alerta.setMensaje("Stock bajo: " + (perfume.getStock() - req.getCantidad()));
            alerta.setTipo("stock_bajo");
            alerta.setFecha(LocalDateTime.now());
            alertaRepo.save(alerta);
        }

        return compra;
    }

    public List<Compra> obtenerComprasPorUsuario(Long idUsuario) {
    return compraRepo.findByIdUsuario(idUsuario);
    }

}
