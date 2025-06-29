package com.example.servicio_postventa.controller;

import com.example.servicio_postventa.model.Devolucion;
import com.example.servicio_postventa.service.DevolucionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v2/devoluciones")
@Tag(name = "Devolución API v2", description = "API versión 2 para la gestión de devoluciones de productos")
@CrossOrigin("*")
public class DevolucionControllerV2 {

    @Autowired
    private DevolucionService servicio;

    @GetMapping
    @Operation(summary = "Listar todas las devoluciones", description = "Obtiene una lista de todas las devoluciones registradas")
    public List<Devolucion> obtenerTodo() {
        return servicio.listarTodas();
    }

    @PostMapping
    @Operation(summary = "Crear una nueva devolución", description = "Registra una nueva devolución en el sistema")
    public Devolucion crear(
            @Parameter(description = "Datos de la nueva devolución")
            @RequestBody Devolucion devolucion) {
        return servicio.guardar(devolucion);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener devolución por ID", description = "Devuelve los detalles de una devolución específica según su ID")
    public ResponseEntity<Devolucion> obtener(
            @Parameter(description = "ID de la devolución a consultar", example = "1")
            @PathVariable Long id) {
        return servicio.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar una devolución", description = "Modifica los datos de una devolución existente")
    public ResponseEntity<Devolucion> actualizar(
            @Parameter(description = "ID de la devolución a actualizar", example = "1")
            @PathVariable Long id,
            @Parameter(description = "Datos actualizados de la devolución")
            @RequestBody Devolucion nueva) {
        Devolucion actualizada = servicio.actualizar(id, nueva);
        return actualizada != null ? ResponseEntity.ok(actualizada) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una devolución", description = "Elimina una devolución específica por su ID")
    public void eliminar(
            @Parameter(description = "ID de la devolución a eliminar", example = "1")
            @PathVariable Long id) {
        servicio.eliminar(id);
    }
}

