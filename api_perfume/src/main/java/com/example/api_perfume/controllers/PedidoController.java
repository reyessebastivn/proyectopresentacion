package com.example.api_perfume.controllers;

import com.example.api_perfume.models.entities.Pedido;
import com.example.api_perfume.services.PedidoService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @GetMapping
    @Operation(summary = "Listar todos los pedidos", description = "Obtiene una lista con todos los pedidos registrados")
    public List<Pedido> listar() {
        return pedidoService.listarTodos();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener pedido por ID", 
               description = "Devuelve un pedido específico usando su ID")
    public ResponseEntity<Pedido> obtener(@PathVariable Long id) {
        Pedido pedido = pedidoService.obtenerPorId(id);
        return pedido != null ? ResponseEntity.ok(pedido) : ResponseEntity.notFound().build();
    }

    @PostMapping
    @Operation(summary = "Crear nuevo pedido", 
               description = "Crea y registra un nuevo pedido")
    public ResponseEntity<Pedido> crear(@Valid @RequestBody Pedido pedido) {
        Pedido creado = pedidoService.crear(pedido);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar pedido", 
               description = "Actualiza los datos de un pedido existente")
    public ResponseEntity<Pedido> actualizar(@PathVariable Long id, @Valid @RequestBody Pedido pedido) {
        Pedido actualizado = pedidoService.actualizar(id, pedido);
        return actualizado != null ? ResponseEntity.ok(actualizado) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar pedido", 
               description = "Elimina un pedido por su ID")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        pedidoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/sucursal/{sucursal}")
    @Operation(summary = "Buscar pedidos por sucursal",  
               description = "Filtra pedidos según la sucursal indicada")
    public List<Pedido> buscarPorSucursal(@PathVariable String sucursal) {
        return pedidoService.buscarPorSucursal(sucursal);
    }

    @GetMapping("/estado/{estado}")
    @Operation(summary = "Buscar pedidos por estado", 
               description = "Filtra pedidos según el estado indicado")
    public List<Pedido> buscarPorEstado(@PathVariable String estado) {
        return pedidoService.buscarPorEstado(estado);
    }
}
