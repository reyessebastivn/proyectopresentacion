package com.example.venta.y.tickets.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.venta.y.tickets.model.Reclamo;
import com.example.venta.y.tickets.service.ReclamoService;

import java.util.List;

@RestController
@RequestMapping("/reclamos")
public class ReclamoController {

    @Autowired
    private ReclamoService reclamoService;

    @GetMapping
    public List<Reclamo> listar() {
        return reclamoService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public Reclamo buscarPorId(@PathVariable Long id) {
        return reclamoService.obtenerPorId(id);
    }

    @GetMapping("/cliente/{clienteNombre}")
    public List<Reclamo> buscarPorCliente(@PathVariable String clienteNombre) {
        return reclamoService.obtenerPorClienteNombre(clienteNombre);
    }

    @GetMapping("/estado/{estado}")
    public List<Reclamo> buscarPorEstado(@PathVariable String estado) {
        return reclamoService.obtenerPorEstado(estado);
    }

    @PostMapping
    public Reclamo crear(@RequestBody Reclamo reclamo) {
        return reclamoService.guardar(reclamo);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        reclamoService.eliminar(id);
    }
}
