package com.example.api_ventas_inventario.controllers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

import com.example.api_ventas_inventario.assamblers.PerfumeAssambler;
import com.example.api_ventas_inventario.models.Perfume;
import com.example.api_ventas_inventario.services.PerfumeService;
import com.example.api_ventas_inventario.services.PerfumeServiceImpl;

@RestController
@RequestMapping("v2/perfumes")
public class PerfumeControllerv2 {

    @Autowired
    private PerfumeAssambler assembler;

    @Autowired
    private  PerfumeService perfumeServicev2;

   @GetMapping("/")
   @Operation(summary = "Obtener todos los perfumes",
   description = "Obtiene una lista con todos los perfumes del inventario")
   public List<Perfume>obtenerTodos(){
        List<EntityModel<Perfume>> perfumes = perfumeServicev2.obtenerTodo()
            .stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());
        return perfumeServicev2.obtenerTodo();     
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
   


   @Operation(summary = "Elimina perfume",
   description = "Eliminar perfume en especifico por su ID")
    @DeleteMapping("/{id}")
    public void deletePerfume(@PathVariable Long id) {
        perfumeServicev2.deleteById(id);
    }
   
   
}
