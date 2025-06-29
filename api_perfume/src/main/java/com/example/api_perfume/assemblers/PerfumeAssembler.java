package com.example.api_perfume.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.example.api_perfume.controllers.PerfumeControllerV2;
import com.example.api_perfume.models.entities.Perfume;

@Component
public class PerfumeAssembler implements RepresentationModelAssembler<Perfume, EntityModel<Perfume>> {

    @Override
    public EntityModel<Perfume> toModel(Perfume perfume){
        return EntityModel.of(perfume,
              linkTo(methodOn(PerfumeControllerV2.class).obtenerUno(perfume.getId())).withSelfRel(),
              linkTo(methodOn(PerfumeControllerV2.class).obtenerTodos()).withRel("perfume")
        );

    }
    
}
