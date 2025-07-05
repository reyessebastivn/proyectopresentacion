package com.example.venta.y.tickets.controller;

import com.example.venta.y.tickets.model.Devolucion;
import com.example.venta.y.tickets.service.DevolucionService;
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

import java.util.List;

@RestController
@RequestMapping("/v2/devoluciones")
@Tag(name = "Devolución API v2", description = "API versión 2 para la gestión de devoluciones")
public class DevolucionControllerV2 {

    @Autowired
    private DevolucionService devolucionService;

    @GetMapping
    @Operation(summary = "Listar todas las devoluciones",
               description = "Obtiene una lista de todas las devoluciones registradas")
    public List<Devolucion> listar() {
        return devolucionService.obtenerTodas();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener devolución por ID",
               description = "Devuelve una devolución específica por su ID")
    public ResponseEntity<Devolucion> obtener(
            @Parameter(description = "ID de la devolución", example = "1")
            @PathVariable Long id) {
        Devolucion devolucion = devolucionService.obtenerPorId(id);
        return devolucion != null ? ResponseEntity.ok(devolucion) : ResponseEntity.notFound().build();
    }

    @PostMapping
    @Operation(
        summary = "Crear una nueva devolución",
        description = "Registra una nueva devolución en el sistema",
        requestBody = @RequestBody(
                description = "Datos de la devolución a registrar",
                required = true,
                content = @Content(schema = @Schema(implementation = Devolucion.class))
        )
    )
    public ResponseEntity<Devolucion> crear(@Valid @org.springframework.web.bind.annotation.RequestBody Devolucion devolucion) {
        Devolucion creada = devolucionService.guardar(devolucion);
        return ResponseEntity.status(HttpStatus.CREATED).body(creada);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar devolución",
               description = "Elimina una devolución específica por su ID")
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID de la devolución", example = "1")
            @PathVariable Long id) {
        devolucionService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/venta/{idVenta}")
    @Operation(summary = "Buscar devoluciones por ID de venta",
               description = "Obtiene todas las devoluciones asociadas a una venta específica")
    public List<Devolucion> buscarPorIdVenta(
            @Parameter(description = "ID de la venta", example = "10")
            @PathVariable Long idVenta) {
        return devolucionService.obtenerPorIdVenta(idVenta);
    }
}