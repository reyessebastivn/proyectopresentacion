package com.example.api_perfume.controllers;

import com.example.api_perfume.models.entities.Sucursal;
import com.example.api_perfume.services.SucursalService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sucursales")
public class SucursalController {

    @Autowired
    private SucursalService sucursalService;

    @GetMapping
    @Operation(summary = "Listar todas las sucursales", description = "Devuelve una lista con todas las sucursales registradas")
    public List<Sucursal> listar() {
        return sucursalService.listarTodas();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener sucursal por ID", 
               description = "Devuelve los detalles de una sucursal específica")
    public ResponseEntity<Sucursal> obtener(@PathVariable Long id) {
        Sucursal s = sucursalService.obtenerPorId(id);
        return s != null ? ResponseEntity.ok(s) : ResponseEntity.notFound().build();
    }

    @PostMapping
    @Operation(summary = "Crear nueva sucursal", 
               description = "Permite registrar una nueva sucursal")
    public ResponseEntity<Sucursal> crear(@Valid @RequestBody Sucursal sucursal) {
        Sucursal creada = sucursalService.crear(sucursal);
        return ResponseEntity.status(HttpStatus.CREATED).body(creada);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar sucursal", 
               description = "Actualiza los datos de una sucursal existente")
    public ResponseEntity<Sucursal> actualizar(@PathVariable Long id, @Valid @RequestBody Sucursal sucursal) {
        Sucursal actualizada = sucursalService.actualizar(id, sucursal);
        return actualizada != null ? ResponseEntity.ok(actualizada) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar sucursal", 
               description = "Elimina una sucursal por su ID")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        sucursalService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/buscar")
    @Operation(summary = "Buscar sucursales por nombre", 
               description = "Permite buscar sucursales filtrando por nombre")
    public List<Sucursal> buscarPorNombre(@RequestParam String nombre) {
        return sucursalService.buscarPorNombre(nombre);
    }
}
