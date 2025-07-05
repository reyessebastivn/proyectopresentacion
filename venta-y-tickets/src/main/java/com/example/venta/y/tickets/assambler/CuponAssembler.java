package com.example.venta.y.tickets.assambler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.example.venta.y.tickets.controller.CuponController;
import com.example.venta.y.tickets.model.Cupon;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class CuponAssembler implements RepresentationModelAssembler<Cupon, EntityModel<Cupon>> {

    @Override
    public EntityModel<Cupon> toModel(Cupon cupon) {
        return EntityModel.of(cupon,
            linkTo(methodOn(CuponController.class).validar(cupon.getCodigo())).withRel("validar"),
            linkTo(methodOn(CuponController.class).crear(cupon)).withRel("crear"),
            linkTo(methodOn(CuponController.class).eliminar(cupon.getCodigo())).withRel("eliminar")
        );
    }
}