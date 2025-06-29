package com.example.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.example.controllers.PermisoControllerv2;
import com.example.models.entities.Permiso;

@Component
public class PermisoModelAssembler implements RepresentationModelAssembler<Permiso, EntityModel<Permiso>> {

    @Override
    public EntityModel<Permiso> toModel(Permiso permiso) {
        return EntityModel.of(permiso,
                linkTo(methodOn(PermisoControllerv2.class).obtenerPorId(permiso.getId())).withSelfRel(),
                linkTo(methodOn(PermisoControllerv2.class).obtenerTodos()).withRel("permisos"));
    }
}
