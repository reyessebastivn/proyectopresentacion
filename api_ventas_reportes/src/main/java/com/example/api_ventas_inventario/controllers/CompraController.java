package com.example.api_ventas_inventario.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.api_ventas_inventario.models.Compra;
import com.example.api_ventas_inventario.models.request.CompraRequest;
import com.example.api_ventas_inventario.services.CompraService;

@RestController
@RequestMapping("/compras")
public class CompraController {

    @Autowired
    private CompraService compraservice;

    @PostMapping
    public ResponseEntity<?> registrarCompra(@RequestBody CompraRequest request) {
        try {
            Compra compra = compraservice.registrarCompra(request);
            return ResponseEntity.ok(compra);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al registrar compra");
        }
    }

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<?> obtenerComprasPorUsuario(@PathVariable Long idUsuario) {
        try {
            return ResponseEntity.ok(compraservice.obtenerComprasPorUsuario(idUsuario));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al obtener compras del usuario");
        }
    }
}