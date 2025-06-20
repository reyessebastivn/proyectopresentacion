package com.example.api_ventas_inventario.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.api_ventas_inventario.models.Perfume;
import com.example.api_ventas_inventario.services.PerfumeService;

@RestController
@RequestMapping("/perfumes")
public class PerfumeController {
    
    private final PerfumeService perfumeService;

    @Autowired
    public PerfumeController(PerfumeService perfumeService) {
        this.perfumeService = perfumeService;
    }

    // Obtener todos los perfumes
    @GetMapping
    public List<Perfume> getAllPerfumes() {
        return perfumeService.getAllPerfumes();
    }

    // Obtener un perfume por ID
    @GetMapping("/{id}")
    public Optional<Perfume> getPerfumeById(@PathVariable Long id) {
        return perfumeService.getPerfumeById(id);
    }

    // Crear o actualizar un perfume
    @PostMapping
    public Perfume savePerfume(@RequestBody Perfume perfume) {
        return perfumeService.savePerfume(perfume);
    }

    // Eliminar un perfume por ID
    @DeleteMapping("/{id}")
    public void deletePerfume(@PathVariable Long id) {
        perfumeService.deletePerfume(id);
    }
}
