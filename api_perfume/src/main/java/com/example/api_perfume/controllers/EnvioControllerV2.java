package com.example.api_perfume.controllers;

import com.example.api_perfume.models.entities.Envio;
import com.example.api_perfume.services.EnvioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v2/envios")
@Tag(name = "Envio API v2", description = "API versión 2 para la gestión de envíos")
public class EnvioControllerV2 {

    @Autowired
    private EnvioService envioService;

    @GetMapping
    @Operation(summary = "Listar todos los envíos", description = "Devuelve una lista de todos los envíos registrados")
    public List<Envio> obtenerTodos() {
        return envioService.listarTodos();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un envío por ID", description = "Devuelve los detalles de un envío específico según su ID")
    public ResponseEntity<Envio> obtenerPorId(
            @Parameter(description = "ID del envío a consultar", example = "1")
            @PathVariable Long id) {
        return envioService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(
        summary = "Crear un nuevo envío",
        description = "Permite registrar un nuevo envío en la base de datos",
        requestBody = @RequestBody(
                description = "Datos del envío a crear",
                required = true,
                content = @Content(
                        schema = @Schema(implementation = Envio.class)
                )
        )
    )
    public ResponseEntity<Envio> crear(@Valid @RequestBody Envio envio) {
        Envio creado = envioService.crear(envio);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @PutMapping("/{id}")
    @Operation(
        summary = "Actualizar un envío",
        description = "Modifica los datos de un envío existente",
        requestBody = @RequestBody(
                description = "Datos actualizados del envío",
                required = true,
                content = @Content(
                        schema = @Schema(implementation = Envio.class)
                )
        )
    )
    public ResponseEntity<Envio> actualizar(
            @Parameter(description = "ID del envío a actualizar", example = "1")
            @PathVariable Long id,
            @Valid @RequestBody Envio envio) {
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
