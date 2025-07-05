package com.example.venta.y.tickets.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.venta.y.tickets.model.Cupon;
import com.example.venta.y.tickets.service.CuponService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
@RequestMapping("cupon")
public class CuponController {

    @Autowired
    private CuponService cuponService; 

    
    @PostMapping("/crear")
    @Operation(summary ="Crear nuevo cupon", 
            description = "Crea un nuevo cupon en el sistema.")
    public String registrarCupon(@Valid @RequestBody Cupon cupon) {
        cuponService.registrarCupon(cupon);
        return "Agregado!";
    }

    @GetMapping("/validar/{codigo}")
    @Operation(summary = "Validar cupon",
               description = "Valida si el cupon ya a sido utilizado.")
    public String validarCupon(@PathVariable String codigo) {
        cuponService.validarCupon(codigo);
        return "Valido!";
        
}
    @DeleteMapping("/{codigo}")
    @Operation(summary = "Eliminar cupon", 
            description = "Elimina un cupón por ID.")
    public String eliminarCupon(@PathVariable String codigo) {
        cuponService.eliminarCuponPorId(codigo);   
        return "Eliminado!";
    }




}

