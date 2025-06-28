package com.example.api_perfume.controllers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import com.example.api_perfume.assemblers.PerfumeAssembler;
import com.example.api_perfume.models.Perfume;
import com.example.api_perfume.services.PerfumeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/v2/perfumes")
@Tag(name = "Perfume Controller", description = "Controlador para gestión de perfumes")
public class PerfumeControllerV2 {

    @Autowired
    private PerfumeAssembler assembler;

    @Autowired
    private PerfumeService perfumeServicev2;

    @GetMapping("/")
    @Operation(summary = "Obtener todos los perfumes", description = "Obtiene una lista HATEOAS de todos los perfumes del inventario")
    public CollectionModel<EntityModel<Perfume>> obtenerTodos() {
        List<EntityModel<Perfume>> perfumes = perfumeServicev2.obtenerTodos()
                .stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(perfumes);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un perfume por ID", description = "Obtiene un perfume específico por su ID")
    public Optional<Perfume> obtenerUno(@PathVariable Long id) {
        return perfumeServicev2.findById(id);
    }

    @PostMapping
    @Operation(summary = "Agregar o actualizar un perfume", description = "Agrega un nuevo perfume o actualiza el stock si ya existe")
    public Perfume save(@Valid @RequestBody Perfume perfume) {
        return perfumeServicev2.save(perfume);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un perfume", description = "Elimina un perfume específico por su ID")
    public void deletePerfume(@PathVariable Long id) {
        perfumeServicev2.eliminar(id);
    }
}
