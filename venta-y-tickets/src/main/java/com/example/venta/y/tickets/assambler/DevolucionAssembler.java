package com.example.venta.y.tickets.assambler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.example.venta.y.tickets.controller.DevolucionController;
import com.example.venta.y.tickets.model.Devolucion;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class DevolucionAssembler implements RepresentationModelAssembler<Devolucion, EntityModel<Devolucion>> {

    @Override
    public EntityModel<Devolucion> toModel(Devolucion devolucion) {
        return EntityModel.of(devolucion,
            linkTo(methodOn(DevolucionController.class).buscarPorId(devolucion.getId())).withSelfRel(),
            linkTo(methodOn(DevolucionController.class).listar()).withRel("todas-las-devoluciones")
        );
    }
}
