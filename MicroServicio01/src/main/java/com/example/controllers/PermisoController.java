package com.example.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.models.entities.Permiso;
import com.example.services.PermisoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/permiso")
@Tag(name = "Permisos", description = "Controlador para gestión de permisos")
public class PermisoController {

    @Autowired
    private PermisoService permisoService;

    @GetMapping
    @Operation(summary = "Listar todos los permisos", description = "Obtiene la lista completa de permisos registrados")
    public List<Permiso> obtenerTodos() {
        return permisoService.listar();
    }

    @PostMapping
    @Operation(summary = "Guardar nuevo permiso", description = "Guarda un nuevo permiso en el sistema")
    public Permiso guardar(
            @Parameter(description = "Permiso a guardar", required = true)
            @Valid @RequestBody Permiso permiso) {
        return permisoService.guardar(permiso);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener permiso por ID", description = "Obtiene un permiso específico por su ID")
    public Permiso obtenerPorId(
            @Parameter(description = "ID del permiso", example = "1", required = true)
            @PathVariable Long id) {
        return permisoService.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Permiso no encontrado"));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar permiso", description = "Elimina un permiso existente por su ID")
    public void eliminar(
            @Parameter(description = "ID del permiso", example = "1", required = true)
            @PathVariable Long id) {
        permisoService.eliminarPorId(id);
    }
}
