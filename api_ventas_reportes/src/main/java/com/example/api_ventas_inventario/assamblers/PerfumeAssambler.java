package com.example.api_ventas_inventario.assamblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.example.api_ventas_inventario.controllers.PerfumeControllerv2;
import com.example.api_ventas_inventario.models.Perfume;

@Component
public class PerfumeAssambler implements RepresentationModelAssembler<Perfume, EntityModel<Perfume>> {

    @Override
    public EntityModel<Perfume> toModel(Perfume perfume) {
        return EntityModel.of(perfume,
            linkTo(methodOn(PerfumeControllerv2.class).obtenerUno(perfume.getId())).withSelfRel(),
            linkTo(methodOn(PerfumeControllerv2.class).obtenerTodos()).withRel("perfumes")
        );
    }
}
