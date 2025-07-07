package com.example.api_perfume.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.api_perfume.models.entities.Envio;
import com.example.api_perfume.services.EnvioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/envios")
public class EnvioController {

    @Autowired
    private EnvioService envioService;

    @GetMapping
    @Operation(summary = "Listar todos los envíos", description = "Devuelve una lista con todos los envíos registrados")
    public List<Envio> obtenerTodos() {
        return envioService.listarTodos();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener envío por ID", 
               description = "Devuelve los detalles de un envío específico")
    public ResponseEntity<Envio> obtenerPorId(@PathVariable Long id) {
        return envioService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Crear nuevo envío", 
               description = "Crea y registra un nuevo envío")
    public ResponseEntity<Envio> crearEnvio(@RequestBody Envio envio) {
        Envio nuevoEnvio = envioService.guardarEnvio(envio);
        return new ResponseEntity<>(nuevoEnvio, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar envío", 
               description = "Actualiza los datos de un envío existente")
    public ResponseEntity<Envio> actualizar(@PathVariable Long id, @Valid @RequestBody Envio envio) {
        Envio actualizado = envioService.actualizar(id, envio);
        if (actualizado == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar envío", 
               description = "Elimina un envío por su ID")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        envioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
