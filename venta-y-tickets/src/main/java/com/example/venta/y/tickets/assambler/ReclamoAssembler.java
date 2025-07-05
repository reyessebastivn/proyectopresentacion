package com.example.venta.y.tickets.assambler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.example.venta.y.tickets.controller.ReclamoController;
import com.example.venta.y.tickets.model.Reclamo;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class ReclamoAssembler implements RepresentationModelAssembler<Reclamo, EntityModel<Reclamo>> {

    @Override
    public EntityModel<Reclamo> toModel(Reclamo reclamo) {
        return EntityModel.of(reclamo,
            linkTo(methodOn(ReclamoController.class).buscarPorId(reclamo.getId())).withSelfRel(),
            linkTo(methodOn(ReclamoController.class).listar()).withRel("todos-los-reclamos")
        );
    }
}
