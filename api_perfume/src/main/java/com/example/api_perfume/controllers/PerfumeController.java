package com.example.api_perfume.controllers;

import com.example.api_perfume.models.ModificarPerfume;
import com.example.api_perfume.models.Perfume;
import com.example.api_perfume.services.PerfumeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/perfumes")
public class PerfumeController {

    @Autowired
    private PerfumeService sPerfume;

    @GetMapping
    public List<Perfume> todos() {
        return sPerfume.obtenerTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Perfume> obtenerUno(@PathVariable long id) {
        Perfume perfume = sPerfume.obtenerUno(id);
        return perfume != null ? ResponseEntity.ok(perfume) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Void> agregar(@Valid @RequestBody Perfume perfume) {
        sPerfume.agregar(perfume);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> actualizar(@PathVariable long id, @Valid @RequestBody ModificarPerfume perfume) {
        sPerfume.modificar(id, perfume);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable long id) {
        sPerfume.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
