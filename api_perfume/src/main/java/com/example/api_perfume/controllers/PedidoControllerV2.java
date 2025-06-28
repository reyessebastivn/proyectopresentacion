package com.example.api_perfume.controllers;

import com.example.api_perfume.models.entities.Pedido;
import com.example.api_perfume.services.PedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v2/pedidos")
@Tag(name = "Pedido API v2", description = "API versión 2 para la gestión de pedidos de perfumes")
public class PedidoControllerV2 {

    @Autowired
    private PedidoService pedidoService;

    @GetMapping
    @Operation(summary = "Listar todos los pedidos", description = "Obtiene una lista de todos los pedidos realizados")
    public List<Pedido> listar() {
        return pedidoService.listarTodos();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener pedido por ID", description = "Devuelve los detalles de un pedido específico por su ID")
    public ResponseEntity<Pedido> obtener(
            @Parameter(description = "ID del pedido a consultar", example = "1")
            @PathVariable Long id) {
        Pedido pedido = pedidoService.obtenerPorId(id);
        return pedido != null ? ResponseEntity.ok(pedido) : ResponseEntity.notFound().build();
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo pedido", description = "Permite registrar un nuevo pedido en el sistema")
    public ResponseEntity<Pedido> crear(
            @Parameter(description = "Objeto Pedido con la información a registrar")
            @RequestBody Pedido pedido) {
        return ResponseEntity.ok(pedidoService.crear(pedido));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un pedido", description = "Permite modificar los datos de un pedido existente")
    public ResponseEntity<Pedido> actualizar(
            @Parameter(description = "ID del pedido a actualizar", example = "1")
            @PathVariable Long id,
            @Parameter(description = "Datos actualizados del pedido")
            @RequestBody Pedido pedido) {
        Pedido actualizado = pedidoService.actualizar(id, pedido);
        return actualizado != null ? ResponseEntity.ok(actualizado) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un pedido", description = "Elimina un pedido específico del sistema por su ID")
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID del pedido a eliminar", example = "1")
            @PathVariable Long id) {
        pedidoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/sucursal/{sucursal}")
    @Operation(summary = "Buscar pedidos por sucursal", description = "Devuelve todos los pedidos asociados a una sucursal específica")
    public List<Pedido> buscarPorSucursal(
            @Parameter(description = "Nombre de la sucursal", example = "Santiago Centro")
            @PathVariable String sucursal) {
        return pedidoService.buscarPorSucursal(sucursal);
    }

    @GetMapping("/estado/{estado}")
    @Operation(summary = "Buscar pedidos por estado", description = "Devuelve todos los pedidos que tienen un estado específico")
    public List<Pedido> buscarPorEstado(
            @Parameter(description = "Estado del pedido", example = "Enviado")
            @PathVariable String estado) {
        return pedidoService.buscarPorEstado(estado);
    }
}
