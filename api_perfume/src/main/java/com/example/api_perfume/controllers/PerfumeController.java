package com.example.api_perfume.controllers;

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

import com.example.api_perfume.models.ModificarPerfume;
import com.example.api_perfume.models.entities.Perfume;
import com.example.api_perfume.services.PerfumeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/perfumes")
public class PerfumeController {

    @Autowired
    private PerfumeService sPerfume;

    @GetMapping("")
    @Operation(summary = "Listar perfumes", description = "Obtiene la lista completa de perfumes registrados")
    public List<Perfume> todos() {
        return sPerfume.obtenerTodos();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener perfume por ID", 
               description = "Devuelve un perfume específico usando su ID")
    public Perfume obtenerUno(@PathVariable long id) {
        System.out.println(">>>>Id buscando:" + id);
        return sPerfume.obtenerUno(id);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar perfume", 
               description = "Elimina un perfume existente por su ID")
    public String eliminar(@PathVariable long id) {
        sPerfume.eliminar(id);
        return "Eliminado!";
    }

    @PostMapping("")
    @Operation(summary = "Agregar perfume", 
               description = "Agrega un nuevo perfume al inventario")
    public String agregar(@Valid @RequestBody Perfume perfume) {
        sPerfume.agregar(perfume);
        return "Creado!";
    }

    @PutMapping("/{id}")
    @Operation(summary = "Modificar perfume", 
               description = "Modifica los datos de un perfume existente")
    public String putMethodName(@PathVariable long id, @RequestBody ModificarPerfume perfume) {
        sPerfume.modificar(id, perfume);
        return "Modificado!";
    }

    
    @PutMapping("/descontarStock/{id}/{cantidad}")
    @Operation(summary = "Descontar stock de un perfume", 
               description = "Descuenta la cantidad especificada del stock de un perfume dado su ID.")
    public ResponseEntity<Perfume> descontarStock(
            @Parameter(description = "ID del perfume", example = "1", required = true)
            @PathVariable(required = true) Long id,

            @Parameter(description = "Cantidad a descontar", example = "2", required = true)
            @PathVariable(required = true) Integer cantidad) {
        
        Perfume perfume = sPerfume.obtenerUno(id);
        if (perfume == null) {
            return ResponseEntity.notFound().build();
        }

        if (perfume.getStock() < cantidad) {
            return ResponseEntity.badRequest().body(perfume);
        }

        perfume.setStock(perfume.getStock() - cantidad);
        sPerfume.save(perfume);

        return ResponseEntity.ok(perfume);
    }
}


