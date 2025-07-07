package com.example.venta.y.tickets.controller;

import com.example.venta.y.tickets.model.Reclamo;
import com.example.venta.y.tickets.service.ReclamoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reclamos")
@Tag(name = "Reclamo API", description = "API para la gestión de Reclamos")
public class ReclamoController {

    @Autowired
    private ReclamoService reclamoService;

    @Operation(summary = "Listar todos los reclamos", description = "Obtiene una lista con todos los reclamos registrados en el sistema.")
    @GetMapping
    public ResponseEntity<List<Reclamo>> listar() {
        return ResponseEntity.ok(reclamoService.obtenerTodos());
    }

    @Operation(summary = "Buscar reclamo por ID", 
               description = "Obtiene la información detallada de un reclamo específico utilizando su ID.")
    @GetMapping("/{id}")
    public ResponseEntity<Reclamo> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(reclamoService.obtenerPorId(id));
    }

    @Operation(summary = "Buscar reclamos por cliente", 
               description = "Obtiene una lista de reclamos realizados por un cliente específico.")
    @GetMapping("/cliente/{clienteNombre}")
    public ResponseEntity<List<Reclamo>> buscarPorCliente(@PathVariable String clienteNombre) {
        return ResponseEntity.ok(reclamoService.obtenerPorClienteNombre(clienteNombre));
    }

    @Operation(summary = "Buscar reclamos por estado", 
               description = "Obtiene una lista de reclamos filtrados por estado (por ejemplo: pendiente, resuelto).")
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Reclamo>> buscarPorEstado(@PathVariable String estado) {
        return ResponseEntity.ok(reclamoService.obtenerPorEstado(estado));
    }

    @Operation(summary = "Crear un nuevo reclamo", 
               description = "Crea y registra un nuevo reclamo en el sistema.")
    @PostMapping
    public ResponseEntity<Reclamo> crear(@Valid @RequestBody Reclamo reclamo) {
        return ResponseEntity.ok(reclamoService.guardar(reclamo));
    }

    @Operation(summary = "Eliminar un reclamo", 
               description = "Elimina un reclamo existente utilizando su ID.")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
        reclamoService.eliminar(id);
        return ResponseEntity.ok("Reclamo eliminado correctamente");
    }
}
