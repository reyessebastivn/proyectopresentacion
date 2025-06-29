package com.example.servicio_postventa.controller;

import com.example.servicio_postventa.model.Reclamo;
import com.example.servicio_postventa.service.ReclamoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v2/reclamos")
@Tag(name = "Reclamo API v2", description = "API versión 2 para la gestión de reclamos de clientes postventa")
@CrossOrigin("*")
public class ReclamoControllerV2 {

    @Autowired
    private ReclamoService reclamoService;

    @GetMapping
    @Operation(summary = "Listar todos los reclamos", description = "Obtiene una lista de todos los reclamos registrados en el sistema")
    public List<Reclamo> listar() {
        return reclamoService.listarTodos();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener reclamo por ID", description = "Devuelve los detalles de un reclamo específico según su ID")
    public ResponseEntity<Reclamo> obtener(
            @Parameter(description = "ID del reclamo a consultar", example = "1")
            @PathVariable Long id) {
        return reclamoService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo reclamo", description = "Permite registrar un nuevo reclamo de cliente")
    public Reclamo crear(
            @Parameter(description = "Datos del nuevo reclamo")
            @RequestBody Reclamo reclamo) {
        return reclamoService.guardar(reclamo);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un reclamo", description = "Permite modificar los datos de un reclamo existente")
    public ResponseEntity<Reclamo> actualizar(
            @Parameter(description = "ID del reclamo a actualizar", example = "1")
            @PathVariable Long id,
            @Parameter(description = "Datos actualizados del reclamo")
            @RequestBody Reclamo reclamo) {
        Reclamo actualizado = reclamoService.actualizar(id, reclamo);
        return actualizado != null ? ResponseEntity.ok(actualizado) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un reclamo", description = "Elimina un reclamo específico por su ID")
    public void eliminar(
            @Parameter(description = "ID del reclamo a eliminar", example = "1")
            @PathVariable Long id) {
        reclamoService.eliminar(id);
    }
}
