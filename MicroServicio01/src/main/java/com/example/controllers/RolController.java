package com.example.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.models.entities.Rol;
import com.example.services.RolService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/rol")
@Tag(name = "Roles", description = "Controlador para gestión de roles")
public class RolController {

    @Autowired
    private RolService rolService;

    @GetMapping
    @Operation(summary = "Listar todos los roles", description = "Obtiene la lista completa de roles registrados")
    public List<Rol> obtenerTodos() {
        return rolService.listar();
    }

    @PostMapping
    @Operation(summary = "Guardar nuevo rol", description = "Guarda un nuevo rol en el sistema")
    public Rol guardar(
            @Parameter(description = "Rol a guardar", required = true)
            @Valid @RequestBody Rol rol) {
        return rolService.guardar(rol);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener rol por ID", description = "Obtiene un rol específico por su ID")
    public Rol obtenerPorId(
            @Parameter(description = "ID del rol", example = "1", required = true)
            @PathVariable Long id) {
        return rolService.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar rol", description = "Elimina un rol existente por su ID")
    public void eliminar(
            @Parameter(description = "ID del rol", example = "1", required = true)
            @PathVariable Long id) {
        rolService.eliminarPorId(id);
    }
}
