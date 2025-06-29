package com.example.api_perfume.controllers;

import com.example.api_perfume.models.entities.Reporte;
import com.example.api_perfume.services.ReporteService;
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

    @GetMapping
    public List<Reporte> listarTodos() {
        return reporteService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reporte> obtenerPorId(@PathVariable Long id) {
        Reporte r = reporteService.obtenerPorId(id);
        return r != null ? ResponseEntity.ok(r) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Reporte> crear(@Valid @RequestBody Reporte reporte) {
        Reporte creado = reporteService.crear(reporte);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Reporte> actualizar(@PathVariable Long id, @Valid @RequestBody Reporte reporte) {
        Reporte actualizado = reporteService.actualizar(id, reporte);
        return actualizado != null ? ResponseEntity.ok(actualizado) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        reporteService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/tipo/{tipo}")
    public List<Reporte> listarPorTipo(@PathVariable String tipo) {
        return reporteService.buscarPorTipo(tipo);
    }

    @GetMapping("/sucursal/{sucursal}")
    public List<Reporte> listarPorSucursal(@PathVariable String sucursal) {
        return reporteService.buscarPorSucursal(sucursal);
    }

    @GetMapping("/fecha")
    public List<Reporte> buscarPorFecha(
            @RequestParam("desde") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
            @RequestParam("hasta") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta) {
        return reporteService.buscarPorRangoDeFecha(desde, hasta);
    }
}
