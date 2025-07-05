package com.example.api_perfume.controllers;


import com.example.api_perfume.models.entities.Venta;
import com.example.api_perfume.services.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ventas")
public class VentaController {

    @Autowired
    private VentaService ventaService;

    // Endpoint para crear una nueva venta (boleta)
    @PostMapping("")
    public Venta crearVenta(@RequestParam Long idUsuario,
                            @RequestParam String sucursal,
                            @RequestParam Long idPerfume,
                            @RequestParam int cantidad) throws Exception {

        // Llama al servicio para procesar la venta
        return ventaService.realizarVenta(idUsuario, sucursal, idPerfume, cantidad);
    }
}
