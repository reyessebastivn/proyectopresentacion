package com.example.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.models.entities.Permiso;
import com.example.services.PermisoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/permiso")
public class PermisoController {

    @Autowired
    private PermisoService permisoService;

    @GetMapping
    public List<Permiso> obtenerTodos() {
        return permisoService.listar();
    }

    @PostMapping
    public Permiso guardar(@Valid @RequestBody Permiso permiso) {
        return permisoService.guardar(permiso);
    }

    @GetMapping("/{id}")
    public Permiso obtenerPorId(@PathVariable Long id) {
        return permisoService.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Permiso no encontrado"));
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        permisoService.eliminarPorId(id);
    }
}
