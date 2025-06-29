package com.example.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import com.example.assemblers.RolModelAssembler;
import com.example.models.entities.Rol;
import com.example.services.RolService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/rol/v2")
@Tag(name = "Roles v2", description = "Controlador de Roles versión 2 con Swagger y HATEOAS")
public class RolControllerv2 {

    @Autowired
    private RolService rolService;

    @Autowired
    private RolModelAssembler assembler;

    @GetMapping
    @Operation(summary = "Listar todos los roles", description = "Obtiene todos los roles con HATEOAS")
    public CollectionModel<EntityModel<Rol>> obtenerTodos() {
        List<EntityModel<Rol>> roles = rolService.listar()
                .stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(roles);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener rol por ID", description = "Devuelve un rol específico con HATEOAS")
    public EntityModel<Rol> obtenerPorId(@PathVariable Long id) {
        Rol rol = rolService.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));

        return assembler.toModel(rol);
    }

    @PostMapping
    @Operation(summary = "Guardar nuevo rol", description = "Guarda un nuevo rol y devuelve el recurso HATEOAS")
    public EntityModel<Rol> guardar(@Valid @RequestBody Rol rol) {
        Rol nuevo = rolService.guardar(rol);
        return assembler.toModel(nuevo);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar rol", description = "Elimina un rol por su ID")
    public void eliminar(@PathVariable Long id) {
        rolService.eliminarPorId(id);
    }
}
