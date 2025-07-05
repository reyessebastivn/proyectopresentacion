package com.example.venta.y.tickets.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.venta.y.tickets.model.Reclamo;
import com.example.venta.y.tickets.service.ReclamoService;

import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/reclamos")
public class ReclamoController {

    @Autowired
    private ReclamoService reclamoService;

    @GetMapping
    public ResponseEntity<List<Reclamo>> listar() {
        return ResponseEntity.ok(reclamoService.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reclamo> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(reclamoService.obtenerPorId(id));
    }

    @GetMapping("/cliente/{clienteNombre}")
    public ResponseEntity<List<Reclamo>> buscarPorCliente(@PathVariable String clienteNombre) {
        return ResponseEntity.ok(reclamoService.obtenerPorClienteNombre(clienteNombre));
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Reclamo>> buscarPorEstado(@PathVariable String estado) {
        return ResponseEntity.ok(reclamoService.obtenerPorEstado(estado));
    }

    @PostMapping
    public ResponseEntity<Reclamo> crear(@Valid @RequestBody Reclamo reclamo) {
        return ResponseEntity.ok(reclamoService.guardar(reclamo));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
        reclamoService.eliminar(id);
        return ResponseEntity.ok("Reclamo eliminado correctamente");
    }
}
