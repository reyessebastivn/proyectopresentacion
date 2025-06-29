package com.example.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.example.controllers.RolControllerv2;
import com.example.models.entities.Rol;

@Component
public class RolModelAssembler implements RepresentationModelAssembler<Rol, EntityModel<Rol>> {

    @Override
    public EntityModel<Rol> toModel(Rol rol) {
        return EntityModel.of(rol,
                linkTo(methodOn(RolControllerv2.class).obtenerPorId(rol.getId())).withSelfRel(),
                linkTo(methodOn(RolControllerv2.class).obtenerTodos()).withRel("roles"));
    }
}
