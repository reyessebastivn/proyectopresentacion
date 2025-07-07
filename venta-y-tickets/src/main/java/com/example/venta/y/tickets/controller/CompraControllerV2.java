package com.example.venta.y.tickets.controller;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.venta.y.tickets.model.Compra;
import com.example.venta.y.tickets.model.response.CompraResponse;
import com.example.venta.y.tickets.service.CompraService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/v2/compras")
@Tag(name = "Compra API V2", description = "API para la gestión de Cupones v2")
public class CompraControllerV2 {
    
    private CompraService compraService;
    

    @PostMapping
    @Operation(summary = "Registrar compra", 
            description = "Registra una nueva compra y devuelve la entidad con enlaces HATEOAS.")
    public ResponseEntity<EntityModel<CompraResponse>> registrarCompra(@RequestBody Compra compra) {
        CompraResponse nuevaCompra = compraService.registrarCompra(compra);

        EntityModel<CompraResponse> resource = EntityModel.of(nuevaCompra);
        resource.add(linkTo(methodOn(CompraController.class).registrarCompra(compra)).withSelfRel());

        return ResponseEntity.ok(resource);
    }

    @GetMapping("/usuario/{usuarioId}")
    @Operation(summary = "Listar compras por usuario", description = "Lista compras de un usuario con enlaces HATEOAS.")
    public CollectionModel<EntityModel<CompraResponse>> listarPorUsuario(@PathVariable Long usuarioId) {
        List<Compra> compras = compraService.obtenerComprasPorUsuario(usuarioId);

        List<EntityModel<CompraResponse>> recursos = compras.stream()
                .map(compra -> {
                    CompraResponse response = compraService.registrarCompra(compra);
                    EntityModel<CompraResponse> resource = EntityModel.of(response);
                    resource.add(linkTo(methodOn(CompraController.class).listarPorUsuario(usuarioId)).withSelfRel());
                    return resource;
                })
                .toList();

        return CollectionModel.of(recursos);
    }
}

