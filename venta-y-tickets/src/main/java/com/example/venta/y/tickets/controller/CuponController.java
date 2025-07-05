package com.example.venta.y.tickets.controller;


import com.example.venta.y.tickets.model.Cupon;
import com.example.venta.y.tickets.service.CuponService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cupones")
public class CuponController {

    @Autowired
    private CuponService cuponService;

    @GetMapping
    public List<Cupon> listar() {
        return cuponService.obtenerTodosLosCupones();
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<Cupon> obtener(@PathVariable String codigo) {
        Cupon cupon = cuponService.obtenerCuponId(codigo);
        return ResponseEntity.ok(cupon);
    }

    @PostMapping
    public ResponseEntity<Cupon> crear(@Valid @RequestBody Cupon cupon) {
        Cupon creado = cuponService.registrarCupon(cupon);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @DeleteMapping("/{codigo}")
    public ResponseEntity<Void> eliminar(@PathVariable String codigo) {
        cuponService.eliminarCuponPorId(codigo);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/validar/{codigo}")
    public ResponseEntity<Cupon> validar(@PathVariable String codigo) {
        Cupon cupon = cuponService.validarCupon(codigo);
        return ResponseEntity.ok(cupon);
    }
}

