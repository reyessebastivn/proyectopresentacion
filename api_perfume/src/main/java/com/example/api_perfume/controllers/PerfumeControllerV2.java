package com.example.api_perfume.controllers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.api_perfume.assemblers.PerfumeAssembler;
import com.example.api_perfume.models.ModificarPerfume;
import com.example.api_perfume.models.Perfume;
import com.example.api_perfume.services.PerfumeService;
import io.swagger.v3.oas.annotations.Operation;

import jakarta.validation.Valid;


@RestController
@RequestMapping("v2/perfumes")
public class PerfumeControllerV2 {

    @Autowired
    private PerfumeAssembler assembler;

    @Autowired
    private  PerfumeService perfumeServicev2;

   @GetMapping("/")
   @Operation(summary = "Obtener todos los perfumes",
   description = "Obtiene una lista con todos los perfumes del inventario")
   public List<Perfume>obtenerTodos(){
        List<EntityModel<Perfume>> perfumes = perfumeServicev2.obtenerTodos()
            .stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());
        return perfumeServicev2.obtenerTodos();     
   }



   @Operation(summary = "Obtener un perfume por ID",
   description = "Obtiene un perfume en especifico por numero de ID")
   @GetMapping("/{id}")
   public Optional<Perfume> obtenerUno(@PathVariable Long id){
        return perfumeServicev2.findById(id);
   }



   @Operation(summary = "Ingresar perfume ya sea nuevo o actualizacion de stock",
   description = "Agrega el perfume al sistema")
   @PostMapping
   public Perfume save(@Valid @RequestBody Perfume perfume) {
        return perfumeServicev2.save(perfume);
   } 
   


   @Operation(summary = "Elimina usuario",
   description = "Elimina a un usuario en especifico por su ID")
    @DeleteMapping("/{id}")
    public void deletePerfume(@PathVariable Long id) {
        perfumeServicev2.eliminar(id);
    }
}
   
