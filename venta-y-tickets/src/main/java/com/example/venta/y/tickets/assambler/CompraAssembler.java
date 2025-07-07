package com.example.venta.y.tickets.assambler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.example.venta.y.tickets.controller.CompraController;
import com.example.venta.y.tickets.model.Compra;

@Component
public class CompraAssembler implements RepresentationModelAssembler<Compra, EntityModel<Compra>> {

    @Override
    public EntityModel<Compra> toModel(Compra compra) {
        return EntityModel.of(compra,
            linkTo(methodOn(CompraController.class).registrarCompra(compra)).withRel("registrar"),
            linkTo(methodOn(CompraController.class).listarPorUsuario(compra.getUsuarioId())).withRel("compras-usuario")
        );
    }
}
