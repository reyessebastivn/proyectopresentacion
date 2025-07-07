package com.example.venta.y.tickets.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.venta.y.tickets.model.Compra;
import com.example.venta.y.tickets.model.response.CompraResponse;
import com.example.venta.y.tickets.service.CompraService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/compras")
@Tag(name = "Compra", description = "API para la visualizacion de compras")
public class CompraController {

    @Autowired
    private CompraService compraService;

    @PostMapping
    @Operation(summary = "Registrar nueva compra", 
               description = "Permite a un usuario comprar un perfume, descontando stock y calculando total.")
    public ResponseEntity<CompraResponse> registrarCompra(@RequestBody Compra compra) {
        CompraResponse nuevaCompra = compraService.registrarCompra(compra);
        return ResponseEntity.ok(nuevaCompra);
    }

    @GetMapping("/usuario/{usuarioId}")
    @Operation(summary = "Listar compras por usuario", 
               description = "Obtiene todas las compras realizadas por un usuario, con nombres.")
    public List<CompraResponse> listarPorUsuario(@PathVariable Long usuarioId) {
        List<Compra> compras = compraService.obtenerComprasPorUsuario(usuarioId);
        return compras.stream()
            .map(compra -> compraService.registrarCompra(compra))
            .toList();
    }
}
