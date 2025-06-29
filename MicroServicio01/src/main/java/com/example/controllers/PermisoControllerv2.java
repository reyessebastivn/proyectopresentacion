package com.example.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import com.example.assemblers.PermisoModelAssembler;
import com.example.models.entities.Permiso;
import com.example.services.PermisoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/permiso/v2")
@Tag(name = "Permisos v2", description = "Controlador de Permisos versión 2 con Swagger y HATEOAS")
public class PermisoControllerv2 {

    @Autowired
    private PermisoService permisoService;

    @Autowired
    private PermisoModelAssembler assembler;

    @GetMapping
    @Operation(summary = "Listar todos los permisos", description = "Devuelve todos los permisos con HATEOAS")
    public CollectionModel<EntityModel<Permiso>> obtenerTodos() {
        List<EntityModel<Permiso>> permisos = permisoService.listar()
                .stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(permisos);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener permiso por ID", description = "Devuelve un permiso específico con HATEOAS")
    public EntityModel<Permiso> obtenerPorId(@PathVariable Long id) {
        Permiso permiso = permisoService.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Permiso no encontrado"));
        return assembler.toModel(permiso);
    }

    @PostMapping
    @Operation(summary = "Guardar nuevo permiso", description = "Guarda un nuevo permiso y devuelve el recurso HATEOAS")
    public EntityModel<Permiso> guardar(@Valid @RequestBody Permiso permiso) {
        Permiso nuevo = permisoService.guardar(permiso);
        return assembler.toModel(nuevo);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar permiso", description = "Elimina un permiso por su ID")
    public void eliminar(@PathVariable Long id) {
        permisoService.eliminarPorId(id);
    }
}
