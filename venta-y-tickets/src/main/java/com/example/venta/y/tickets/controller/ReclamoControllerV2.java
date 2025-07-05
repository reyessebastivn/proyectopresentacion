package com.example.venta.y.tickets.controller;

import com.example.venta.y.tickets.model.Reclamo;
import com.example.venta.y.tickets.service.ReclamoService;
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
@RequestMapping("/v2/reclamos")
@Tag(name = "Reclamo API v2", description = "API versión 2 para la gestión de reclamos")
public class ReclamoControllerV2 {

    @Autowired
    private ReclamoService reclamoService;

    @GetMapping
    @Operation(summary = "Listar todos los reclamos",
               description = "Obtiene una lista de todos los reclamos registrados")
    public List<Reclamo> listar() {
        return reclamoService.obtenerTodos();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener reclamo por ID",
               description = "Devuelve un reclamo específico según su ID")
    public ResponseEntity<Reclamo> obtener(
            @Parameter(description = "ID del reclamo a consultar", example = "1")
            @PathVariable Long id) {
        Reclamo reclamo = reclamoService.obtenerPorId(id);
        return reclamo != null ? ResponseEntity.ok(reclamo) : ResponseEntity.notFound().build();
    }

    @PostMapping
    @Operation(
        summary = "Crear un nuevo reclamo",
        description = "Registra un nuevo reclamo en el sistema",
        requestBody = @RequestBody(
                description = "Datos del reclamo a registrar",
                required = true,
                content = @Content(schema = @Schema(implementation = Reclamo.class))
        )
    )
    public ResponseEntity<Reclamo> crear(@Valid @org.springframework.web.bind.annotation.RequestBody Reclamo reclamo) {
        Reclamo creado = reclamoService.guardar(reclamo);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar reclamo",
               description = "Elimina un reclamo específico por su ID")
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID del reclamo a eliminar", example = "1")
            @PathVariable Long id) {
        reclamoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/cliente/{clienteNombre}")
    @Operation(summary = "Buscar reclamos por cliente",
               description = "Obtiene todos los reclamos hechos por un cliente específico")
    public List<Reclamo> buscarPorCliente(
            @Parameter(description = "Nombre del cliente", example = "Juan Pérez")
            @PathVariable String clienteNombre) {
        return reclamoService.obtenerPorClienteNombre(clienteNombre);
    }

    @GetMapping("/estado/{estado}")
    @Operation(summary = "Buscar reclamos por estado",
               description = "Obtiene todos los reclamos con un estado específico")
    public List<Reclamo> buscarPorEstado(
            @Parameter(description = "Estado del reclamo", example = "Pendiente")
            @PathVariable String estado) {
        return reclamoService.obtenerPorEstado(estado);
    }
}
