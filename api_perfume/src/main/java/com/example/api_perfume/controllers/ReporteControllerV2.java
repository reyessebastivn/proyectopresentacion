package com.example.api_perfume.controllers;

import com.example.api_perfume.models.entities.Reporte;
import com.example.api_perfume.services.ReporteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/v2/reportes")
@Tag(name = "Reporte API v2", description = "API versión 2 para la gestión de reportes de perfumes")
public class ReporteControllerV2 {

    @Autowired
    private ReporteService reporteService;

    @GetMapping
    @Operation(summary = "Listar todos los reportes", description = "Devuelve una lista con todos los reportes almacenados")
    public List<Reporte> listarTodos() {
        return reporteService.obtenerTodos();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener reporte por ID", description = "Obtiene un reporte específico según su ID")
    public ResponseEntity<Reporte> obtenerPorId(
            @Parameter(description = "ID del reporte a buscar", example = "1")
            @PathVariable Long id) {
        Reporte r = reporteService.obtenerPorId(id);
        return r != null ? ResponseEntity.ok(r) : ResponseEntity.notFound().build();
    }

    @PostMapping
    @Operation(
        summary = "Crear un nuevo reporte",
        description = "Permite crear y guardar un nuevo reporte",
        requestBody = @RequestBody(
                description = "Objeto reporte a crear",
                required = true,
                content = @Content(schema = @Schema(implementation = Reporte.class))
        )
    )
    public ResponseEntity<Reporte> crear(@Valid @org.springframework.web.bind.annotation.RequestBody Reporte reporte) {
        Reporte creado = reporteService.crear(reporte);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @PutMapping("/{id}")
    @Operation(
        summary = "Actualizar un reporte",
        description = "Permite actualizar los datos de un reporte existente",
        requestBody = @RequestBody(
                description = "Datos actualizados del reporte",
                required = true,
                content = @Content(schema = @Schema(implementation = Reporte.class))
        )
    )
    public ResponseEntity<Reporte> actualizar(
            @Parameter(description = "ID del reporte a actualizar", example = "1")
            @PathVariable Long id,
            @Valid @org.springframework.web.bind.annotation.RequestBody Reporte reporte) {
        Reporte actualizado = reporteService.actualizar(id, reporte);
        return actualizado != null ? ResponseEntity.ok(actualizado) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un reporte", description = "Elimina un reporte específico por su ID")
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID del reporte a eliminar", example = "1")
            @PathVariable Long id) {
        reporteService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/tipo/{tipo}")
    @Operation(summary = "Listar reportes por tipo", description = "Devuelve todos los reportes filtrados por tipo")
    public List<Reporte> listarPorTipo(
            @Parameter(description = "Tipo de reporte a filtrar", example = "Ventas")
            @PathVariable String tipo) {
        return reporteService.buscarPorTipo(tipo);
    }

    @GetMapping("/sucursal/{sucursal}")
    @Operation(summary = "Listar reportes por sucursal", description = "Devuelve todos los reportes asociados a una sucursal específica")
    public List<Reporte> listarPorSucursal(
            @Parameter(description = "Nombre de la sucursal a filtrar", example = "Santiago")
            @PathVariable String sucursal) {
        return reporteService.buscarPorSucursal(sucursal);
    }

    @GetMapping("/fecha")
    @Operation(summary = "Listar reportes por rango de fechas", description = "Devuelve los reportes generados entre dos fechas específicas")
    public List<Reporte> buscarPorFecha(
            @Parameter(description = "Fecha de inicio", example = "2024-01-01")
            @RequestParam("desde") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
            @Parameter(description = "Fecha de fin", example = "2024-12-31")
            @RequestParam("hasta") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta) {
        return reporteService.buscarPorRangoDeFecha(desde, hasta);
    }
}
