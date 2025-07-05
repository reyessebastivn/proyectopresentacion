package com.example.venta.y.tickets.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.venta.y.tickets.model.Devolucion;
import com.example.venta.y.tickets.service.DevolucionService;

@RestController
@RequestMapping("/devoluciones")
public class DevolucionController {

    @Autowired
    private DevolucionService devolucionService;

    @GetMapping
    public List<Devolucion> listar() {
        return devolucionService.obtenerTodas();
    }

    @GetMapping("/{id}")
    public Devolucion buscarPorId(@PathVariable Long id) {
        return devolucionService.obtenerPorId(id);
    }

    @GetMapping("/venta/{idVenta}")
    public List<Devolucion> buscarPorIdVenta(@PathVariable Long idVenta) {
        return devolucionService.obtenerPorIdVenta(idVenta);
    }

    @PostMapping
    public Devolucion crear(@RequestBody Devolucion devolucion) {
        return devolucionService.guardar(devolucion);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        devolucionService.eliminar(id);
    }
}