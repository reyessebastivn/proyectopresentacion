package com.example.venta.y.tickets.controller;

import com.example.venta.y.tickets.model.Cupon;
import com.example.venta.y.tickets.service.CuponService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v2/cupones")
@Tag(name = "Cupón API v2", description = "API versión 2 para la gestión de cupones")
public class CuponControllerV2 {

    @Autowired
    private CuponService cuponService;

    @PostMapping("/crear")
    @Operation(
        summary = "Crear nuevo cupón",
        description = "Registra un nuevo cupón en el sistema",
        requestBody = @RequestBody(
                description = "Datos del cupón a registrar",
                required = true,
                content = @Content(schema = @Schema(implementation = Cupon.class))
        )
    )
    public ResponseEntity<Cupon> registrarCupon(@Valid @org.springframework.web.bind.annotation.RequestBody Cupon cupon) {
        Cupon creado = cuponService.registrarCupon(cupon);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @GetMapping("/validar/{codigo}")
    @Operation(summary = "Validar cupón", description = "Verifica si un cupón es válido y está activo")
    public ResponseEntity<Cupon> validarCupon(
            @Parameter(description = "Código del cupón", example = "ABC123")
            @PathVariable String codigo) {
        Cupon cupon = cuponService.validarCupon(codigo);
        return ResponseEntity.ok(cupon);
    }

    @DeleteMapping("/{codigo}")
    @Operation(summary = "Eliminar cupón", description = "Elimina un cupón por su código")
    public ResponseEntity<Void> eliminarCupon(
            @Parameter(description = "Código del cupón", example = "ABC123")
            @PathVariable String codigo) {
        cuponService.eliminarCuponPorId(codigo);
        return ResponseEntity.noContent().build();
    }
}
