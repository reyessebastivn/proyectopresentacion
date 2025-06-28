package com.example.api_perfume.controllers;

import com.example.api_perfume.models.entities.Sucursal;
import com.example.api_perfume.services.SucursalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v2/sucursales")
@Tag(name = "Sucursal API v2", description = "API versión 2 para la gestión de sucursales de la tienda de perfumes")
public class SucursalControllerV2 {

    @Autowired
    private SucursalService sucursalService;

    @GetMapping
    @Operation(summary = "Listar todas las sucursales", description = "Obtiene una lista de todas las sucursales disponibles")
    public List<Sucursal> listar() {
        return sucursalService.listarTodas();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener sucursal por ID", description = "Devuelve los datos de una sucursal específica según su ID")
    public ResponseEntity<Sucursal> obtener(
            @Parameter(description = "ID de la sucursal a obtener", example = "1")
            @PathVariable Long id) {
        Sucursal s = sucursalService.obtenerPorId(id);
        return s != null ? ResponseEntity.ok(s) : ResponseEntity.notFound().build();
    }

    @PostMapping
    @Operation(summary = "Crear una nueva sucursal", description = "Permite registrar una nueva sucursal en el sistema")
    public ResponseEntity<Sucursal> crear(
            @Parameter(description = "Datos de la nueva sucursal a crear")
            @RequestBody Sucursal sucursal) {
        return ResponseEntity.ok(sucursalService.crear(sucursal));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar sucursal", description = "Permite modificar los datos de una sucursal existente")
    public ResponseEntity<Sucursal> actualizar(
            @Parameter(description = "ID de la sucursal a actualizar", example = "1")
            @PathVariable Long id,
            @Parameter(description = "Datos actualizados de la sucursal")
            @RequestBody Sucursal sucursal) {
        Sucursal actualizada = sucursalService.actualizar(id, sucursal);
        return actualizada != null ? ResponseEntity.ok(actualizada) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar sucursal", description = "Elimina una sucursal específica por su ID")
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID de la sucursal a eliminar", example = "1")
            @PathVariable Long id) {
        sucursalService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/buscar")
    @Operation(summary = "Buscar sucursales por nombre", description = "Filtra las sucursales cuyo nombre coincida total o parcialmente")
    public List<Sucursal> buscarPorNombre(
            @Parameter(description = "Nombre o parte del nombre de la sucursal", example = "Santiago Centro")
            @RequestParam String nombre) {
        return sucursalService.buscarPorNombre(nombre);
    }
}
