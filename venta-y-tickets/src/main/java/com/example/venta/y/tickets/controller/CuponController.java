package com.example.venta.y.tickets.controller;

import com.example.venta.y.tickets.model.Cupon;
import com.example.venta.y.tickets.service.CuponService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cupones")
@Tag(name = "Cupones API", description = "API para la gestión de Cupones")
public class CuponController {

    @Autowired
    private CuponService cuponService;

    @Operation(summary = "Listar todos los cupones", description = "Obtiene una lista de todos los cupones registrados en el sistema.")
    @GetMapping
    public List<Cupon> listar() {
        return cuponService.obtenerTodosLosCupones();
    }

    @Operation(summary = "Obtener un cupón por código",     
               description = "Devuelve la información de un cupón específico dado su código.")
    @GetMapping("/{codigo}")
    public ResponseEntity<Cupon> obtener(@PathVariable String codigo) {
        Cupon cupon = cuponService.obtenerCuponId(codigo);
        return ResponseEntity.ok(cupon);
    }

    @Operation(summary = "Crear un nuevo cupón", 
               description = "Crea y guarda un nuevo cupón en la base de datos.")
    @PostMapping
    public ResponseEntity<Cupon> crear(@Valid @RequestBody Cupon cupon) {
        Cupon creado = cuponService.registrarCupon(cupon);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @Operation(summary = "Eliminar un cupón", 
               description = "Elimina un cupón existente utilizando su código.")
    @DeleteMapping("/{codigo}")
    public ResponseEntity<Void> eliminar(@PathVariable String codigo) {
        cuponService.eliminarCuponPorId(codigo);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Validar un cupón", 
               description = "Verifica si un cupón específico es válido.")
    @GetMapping("/validar/{codigo}")
    public ResponseEntity<Cupon> validar(@PathVariable String codigo) {
        Cupon cupon = cuponService.validarCupon(codigo);
        return ResponseEntity.ok(cupon);
    }
}
