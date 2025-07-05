package com.example.venta.y.tickets.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.venta.y.tickets.model.Pago;
import com.example.venta.y.tickets.service.PagoService;

@RestController
@RequestMapping("pago")
public class PagoController {

    @Autowired
    private PagoService pagoService;

     @GetMapping("/usuario/{idUsuario}")
        public List<Pago> obtenerPagosPorUsuarioId(@PathVariable Long idUsuario) {
        return pagoService.obtenerPagosPorIdUsuario(idUsuario);
    }
}