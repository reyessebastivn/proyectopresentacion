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

import com.example.servicio_postventa.model.Reclamo;
import com.example.servicio_postventa.service.ReclamoService;

@RestController
@RequestMapping("/api/reclamos")
@CrossOrigin("*")
public class ReclamoController {

    @Autowired
    private ReclamoService reclamoService;

    @GetMapping
    public List<Reclamo> listar() {
        return reclamoService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reclamo> obtener(@PathVariable Long id) {
        return reclamoService.obtenerPorId(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Reclamo crear(@RequestBody Reclamo reclamo) {
        return reclamoService.guardar(reclamo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Reclamo> actualizar(@PathVariable Long id, @RequestBody Reclamo reclamo) {
        Reclamo actualizado = reclamoService.actualizar(id, reclamo);
        return actualizado != null ? ResponseEntity.ok(actualizado) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        reclamoService.eliminar(id);
    }
}
