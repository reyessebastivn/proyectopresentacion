package com.example.servicio_postventa.assamblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.example.servicio_postventa.controller.DevolucionControllerV2;
import com.example.servicio_postventa.model.Devolucion;

@Component
public class PostVentasAssembler implements RepresentationModelAssembler<Devolucion, EntityModel<Devolucion>> {

    @Override
    public EntityModel<Devolucion> toModel(Devolucion devolucion){
        return EntityModel.of(devolucion,
              linkTo(methodOn(DevolucionControllerV2.class).obtener(devolucion.getId())).withSelfRel(),
              linkTo(methodOn(DevolucionControllerV2.class).obtener(null)).withRel("perfume")
        );

    }
    
}
