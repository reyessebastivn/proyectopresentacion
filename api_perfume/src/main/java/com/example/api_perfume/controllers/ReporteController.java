package com.example.api_perfume.controllers;

import com.example.api_perfume.models.entities.Reporte;
import com.example.api_perfume.services.ReporteService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/reportes")
public class ReporteController {

    @Autowired
    private ReporteService reporteService;

    @Operation(summary = "Listar todos los reportes", description = "Obtiene una lista con todos los reportes registrados en el sistema.")
    @GetMapping
    public List<Reporte> listarTodos() {
        return reporteService.obtenerTodos();
    }

    @Operation(summary = "Obtener un reporte por ID", 
               description = "Devuelve los detalles de un reporte específico utilizando su ID.")
    @GetMapping("/{id}")
    public ResponseEntity<Reporte> obtenerPorId(@PathVariable Long id) {
        Reporte r = reporteService.obtenerPorId(id);
        return r != null ? ResponseEntity.ok(r) : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Crear un nuevo reporte",   
               description = "Crea y registra un nuevo reporte en el sistema.")
    @PostMapping
    public ResponseEntity<Reporte> crear(@Valid @RequestBody Reporte reporte) {
        Reporte creado = reporteService.crear(reporte);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @Operation(summary = "Actualizar un reporte", 
               description = "Actualiza la información de un reporte existente utilizando su ID.")
    @PutMapping("/{id}")
    public ResponseEntity<Reporte> actualizar(@PathVariable Long id, @Valid @RequestBody Reporte reporte) {
        Reporte actualizado = reporteService.actualizar(id, reporte);
        return actualizado != null ? ResponseEntity.ok(actualizado) : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Eliminar un reporte", 
               description = "Elimina un reporte existente utilizando su ID.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        reporteService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Listar reportes por tipo", 
               description = "Obtiene una lista de reportes filtrados por tipo.")
    @GetMapping("/tipo/{tipo}")
    public List<Reporte> listarPorTipo(@PathVariable String tipo) {
        return reporteService.buscarPorTipo(tipo);
    }

    @Operation(summary = "Listar reportes por sucursal", 
               description = "Obtiene una lista de reportes asociados a una sucursal específica.")
    @GetMapping("/sucursal/{sucursal}")
    public List<Reporte> listarPorSucursal(@PathVariable String sucursal) {
        return reporteService.buscarPorSucursal(sucursal);
    }

    @Operation(summary = "Buscar reportes por rango de fechas", 
               description = "Obtiene una lista de reportes dentro de un rango de fechas especificado (desde y hasta).")
    @GetMapping("/fecha")
    public List<Reporte> buscarPorFecha(
            @RequestParam("desde") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
            @RequestParam("hasta") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta) {
        return reporteService.buscarPorRangoDeFecha(desde, hasta);
    }
}



