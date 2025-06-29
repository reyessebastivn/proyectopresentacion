package com.example.api_perfume.controllers;

import com.example.api_perfume.assemblers.PerfumeAssembler;
import com.example.api_perfume.models.Perfume;
import com.example.api_perfume.services.PerfumeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v2/perfumes")
@Tag(name = "Perfume API v2", description = "API versión 2 para la gestión de perfumes (con HATEOAS)")
public class PerfumeControllerV2 {

    @Autowired
    private PerfumeAssembler assembler;

    @Autowired
    private PerfumeService perfumeServicev2;

    @GetMapping
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
    public ResponseEntity<EntityModel<Perfume>> obtenerUno(
            @Parameter(description = "ID del perfume a obtener", example = "1")
            @PathVariable Long id) {
        return perfumeServicev2.findById(id)
                .map(assembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(
        summary = "Agregar o actualizar un perfume",
        description = "Agrega un nuevo perfume o actualiza el stock si ya existe",
        requestBody = @RequestBody(
                description = "Datos del perfume a crear o actualizar",
                required = true,
                content = @Content(schema = @Schema(implementation = Perfume.class))
        )
    )
    public ResponseEntity<Perfume> save(@Valid @org.springframework.web.bind.annotation.RequestBody Perfume perfume) {
        Perfume saved = perfumeServicev2.save(perfume);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un perfume", description = "Elimina un perfume específico por su ID")
    public ResponseEntity<Void> deletePerfume(
            @Parameter(description = "ID del perfume a eliminar", example = "1")
            @PathVariable Long id) {
        perfumeServicev2.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
