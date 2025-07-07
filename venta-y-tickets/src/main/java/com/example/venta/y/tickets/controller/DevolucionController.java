package com.example.venta.y.tickets.controller;

import java.util.List;

import com.example.venta.y.tickets.model.Devolucion;
import com.example.venta.y.tickets.service.DevolucionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/devoluciones")
@Tag(name = "Devoluciones API", description = "API para la gestión de devoluciones")
public class DevolucionController {

    @Autowired
    private DevolucionService devolucionService;

    @Operation(summary = "Listar todas las devoluciones", description = "Obtiene una lista con todas las devoluciones registradas en el sistema.")
    @GetMapping
    public ResponseEntity<List<Devolucion>> listar() {
        return ResponseEntity.ok(devolucionService.obtenerTodas());
    }

    @Operation(summary = "Buscar devolución por ID", 
               description = "Obtiene la información detallada de una devolución específica utilizando su ID.")
    @GetMapping("/{id}")
    public ResponseEntity<Devolucion> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(devolucionService.obtenerPorId(id));
    }

    @Operation(summary = "Buscar devoluciones por ID de venta", 
               description = "Obtiene todas las devoluciones asociadas a una venta específica mediante el ID de la venta.")
    @GetMapping("/venta/{idVenta}")
    public ResponseEntity<List<Devolucion>> buscarPorIdVenta(@PathVariable Long idVenta) {
        return ResponseEntity.ok(devolucionService.obtenerPorIdVenta(idVenta));
    }

    @Operation(summary = "Crear nueva devolución", 
               description = "Crea y registra una nueva devolución en el sistema.")
    @PostMapping
    public ResponseEntity<Devolucion> crear(@Valid @RequestBody Devolucion devolucion) {
        return ResponseEntity.ok(devolucionService.guardar(devolucion));
    }

    @Operation(summary = "Eliminar devolución", 
               description = "Elimina una devolución existente utilizando su ID.")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
        devolucionService.eliminar(id);
        return ResponseEntity.ok("Devolución eliminada correctamente");
    }
}
