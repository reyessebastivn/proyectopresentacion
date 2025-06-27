package com.example.servicio_postventa.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.servicio_postventa.model.Devolucion;
import com.example.servicio_postventa.service.DevolucionService;

@RestController
@RequestMapping("/api/devoluciones")
@CrossOrigin("*")
public class DevolucionController {

    @Autowired
    private DevolucionService servicio;

    @GetMapping
    public List<Devolucion> listar() {
        return servicio.listarTodas();
    }

    @PostMapping
    public Devolucion crear(@RequestBody Devolucion devolucion) {
        return servicio.guardar(devolucion);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Devolucion> obtener(@PathVariable Long id) {
        return servicio.obtenerPorId(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Devolucion> actualizar(@PathVariable Long id, @RequestBody Devolucion nueva) {
        Devolucion actualizada = servicio.actualizar(id, nueva);
        return actualizada != null ? ResponseEntity.ok(actualizada) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        servicio.eliminar(id);
    }
}
