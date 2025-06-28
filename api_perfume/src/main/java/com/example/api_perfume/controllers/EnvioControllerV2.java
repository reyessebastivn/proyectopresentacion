package com.example.api_perfume.controllers;

import com.example.api_perfume.models.entities.Envio;
import com.example.api_perfume.services.EnvioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v2/envios")
@Tag(name = "Envio API v2", description = "API versión 2 para la gestión de envíos de perfumes")
public class EnvioControllerV2 {

    @Autowired
    private EnvioService envioService;

    @GetMapping
    @Operation(summary = "Listar todos los envíos", description = "Obtiene una lista de todos los envíos registrados")
    public List<Envio> obtenerTodos() {
        return envioService.listarTodos();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener envío por ID", description = "Devuelve los detalles de un envío específico según su ID")
    public ResponseEntity<Envio> obtenerPorId(
            @Parameter(description = "ID del envío a consultar", example = "1")
            @PathVariable Long id) {
        return envioService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo envío", description = "Registra un nuevo envío en el sistema")
    public Envio crear(
            @Parameter(description = "Datos del nuevo envío")
            @RequestBody Envio envio) {
        return envioService.crear(envio);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un envío", description = "Modifica los datos de un envío existente")
    public ResponseEntity<Envio> actualizar(
            @Parameter(description = "ID del envío a actualizar", example = "1")
            @PathVariable Long id,
            @Parameter(description = "Datos actualizados del envío")
            @RequestBody Envio envio) {
        Envio actualizado = envioService.actualizar(id, envio);
        if (actualizado == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un envío", description = "Elimina un envío específico por su ID")
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID del envío a eliminar", example = "1")
            @PathVariable Long id) {
        envioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
