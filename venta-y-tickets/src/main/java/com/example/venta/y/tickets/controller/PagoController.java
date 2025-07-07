package com.example.venta.y.tickets.controller;

import java.util.List;

import com.example.venta.y.tickets.model.Pago;
import com.example.venta.y.tickets.service.PagoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pago")
@Tag(name = "Pago API", description = "API para la gestión de pagos")
public class PagoController {

    @Autowired
    private PagoService pagoService;

    @Operation(summary = "Obtener pagos por ID de usuario", description = "Obtiene la lista de pagos realizados por un usuario específico utilizando su ID.")
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<Pago>> obtenerPagosPorUsuarioId(@PathVariable Long idUsuario) {
        return ResponseEntity.ok(pagoService.obtenerPagosPorIdUsuario(idUsuario));
    }
}
