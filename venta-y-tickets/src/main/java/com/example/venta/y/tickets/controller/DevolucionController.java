package com.example.venta.y.tickets.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.venta.y.tickets.model.Devolucion;
import com.example.venta.y.tickets.service.DevolucionService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/devoluciones")
public class DevolucionController {

    @Autowired
    private DevolucionService devolucionService;

    @GetMapping
    public ResponseEntity<List<Devolucion>> listar() {
        return ResponseEntity.ok(devolucionService.obtenerTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Devolucion> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(devolucionService.obtenerPorId(id));
    }

    @GetMapping("/venta/{idVenta}")
    public ResponseEntity<List<Devolucion>> buscarPorIdVenta(@PathVariable Long idVenta) {
        return ResponseEntity.ok(devolucionService.obtenerPorIdVenta(idVenta));
    }

    @PostMapping
    public ResponseEntity<Devolucion> crear(@Valid @RequestBody Devolucion devolucion) {
        return ResponseEntity.ok(devolucionService.guardar(devolucion));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
        devolucionService.eliminar(id);
        return ResponseEntity.ok("Devolución eliminada correctamente");
    }
}