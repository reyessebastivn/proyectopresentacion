package com.example.api_perfume.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.api_perfume.models.ModificarPerfume;
import com.example.api_perfume.models.Perfume;
import com.example.api_perfume.services.PerfumeService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/perfumes")
public class PerfumeController {

    
    @Autowired
    private PerfumeService sPerfume;

    @GetMapping("")
    public List<Perfume> todos(){
        return sPerfume.obtenerTodos();
    }

    
    @GetMapping("/{id}")
    public Perfume obtenerUno(@PathVariable long id){
        System.out.println(">>>>Id buscando:"+id);
        return sPerfume.obtenerUno(id);
    }

    @DeleteMapping("/{id}")
    public String eliminar(@PathVariable long id){
        sPerfume.eliminar(id);
        return "Eliminado!";
    }

    @PostMapping("")
    public String agregar(@Valid @RequestBody Perfume perfume){
        sPerfume.agregar(perfume);
        return "Agregado!";
    }

     @PutMapping("/{id}")
    public String putMethodName(@PathVariable long id, @RequestBody ModificarPerfume perfume) {
        sPerfume.modificar(id, perfume);
        return "Modificado!";
    }
}
