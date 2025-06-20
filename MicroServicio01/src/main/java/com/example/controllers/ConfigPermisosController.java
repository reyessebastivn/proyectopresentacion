package com.example.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.models.entities.ConfigPermisos;
import com.example.services.ConfigPermisosService;
@RestController
@RequestMapping("/api/permisos")

public class ConfigPermisosController {


    @Autowired
    private ConfigPermisosService permisoService;

    @GetMapping
    public List<ConfigPermisos> listarTodos() {
        return permisoService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConfigPermisos> obtenerPorId(@PathVariable Long id) {
        ConfigPermisos permiso = permisoService.obtenerPorId(id);
        if (permiso != null) return ResponseEntity.ok(permiso);
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<ConfigPermisos> crear(@RequestBody ConfigPermisos permiso) {
        return ResponseEntity.ok(permisoService.crear(permiso));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ConfigPermisos> actualizar(@PathVariable Long id, @RequestBody ConfigPermisos permiso) {
        ConfigPermisos actualizado = permisoService.actualizar(id, permiso);
        if (actualizado != null) return ResponseEntity.ok(actualizado);
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        permisoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/rol/{rol}")
    public List<ConfigPermisos> listarPorRol(@PathVariable String rol) {
        return permisoService.obtenerPorRol(rol);
    }
    
}
