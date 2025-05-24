package com.example.api_ventas_inventario.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.api_ventas_inventario.models.Compra;
@RestController
@RequestMapping("/compras")
public class CompraController {
    
    /*private compraService compraService;

    @PostMapping
    public Compra registrarCompra(@RequestBody Compra compra) {
        return compraService.registrarCompra(compra);
    }*/
}
