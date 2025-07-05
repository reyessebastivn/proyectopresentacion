package com.example.venta.y.tickets.controller;

import com.example.venta.y.tickets.model.Pago;
import com.example.venta.y.tickets.service.PagoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v2/pagos")
@Tag(name = "Pago API v2", description = "API versión 2 para la gestión de pagos")
public class PagoControllerV2 {

    @Autowired
    private PagoService pagoService;

    @GetMapping("/usuario/{idUsuario}")
    @Operation(summary = "Obtener pagos por ID de usuario",
               description = "Devuelve todos los pagos realizados por un usuario específico")
    public ResponseEntity<List<Pago>> obtenerPagosPorUsuarioId(
            @Parameter(description = "ID del usuario", example = "1")
            @PathVariable Long idUsuario) {
        return ResponseEntity.ok(pagoService.obtenerPagosPorIdUsuario(idUsuario));
    }
}