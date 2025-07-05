package com.example.venta.y.tickets.assambler;


import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.example.venta.y.tickets.controller.PagoController;
import com.example.venta.y.tickets.model.Pago;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class PagoAssembler implements RepresentationModelAssembler<Pago, EntityModel<Pago>> {

    @Override
    public EntityModel<Pago> toModel(Pago pago) {
        return EntityModel.of(pago,
            linkTo(methodOn(PagoController.class).obtenerPagosPorUsuarioId(pago.getIdUsuario())).withRel("pagos-usuario"),
            linkTo(methodOn(PagoController.class).obtenerPagosPorUsuarioId(pago.getIdUsuario())).withSelfRel()
        );
    }
}
