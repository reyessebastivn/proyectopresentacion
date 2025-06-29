package com.example.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import com.example.assemblers.UserModelAssembler;
import com.example.models.entities.User;
import com.example.models.requests.UserCreate;
import com.example.models.requests.UserUpdate;
import com.example.services.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/usuario/v2")
@Tag(name = "Usuarios v2", description = "Controlador versión 2 con Swagger y HATEOAS")
public class UserControllerv2 {

    @Autowired
    private UserService userService;

    @Autowired
    private UserModelAssembler assembler;

    @GetMapping
    @Operation(summary = "Listar todos los usuarios", description = "Devuelve todos los usuarios con enlaces HATEOAS")
    public CollectionModel<EntityModel<User>> obtenerTodos() {
        List<EntityModel<User>> usuarios = userService.obtenerTodos()
                .stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(usuarios);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener usuario por ID", description = "Devuelve un usuario específico por su ID con HATEOAS")
    public EntityModel<User> obtenerPorId(@PathVariable int id) {
        User usuario = userService.obtenerPorId(id);
        return assembler.toModel(usuario);
    }

    @PostMapping
    @Operation(summary = "Registrar un nuevo usuario", description = "Registra un usuario y devuelve el recurso con HATEOAS")
    public EntityModel<User> registrar(@Valid @RequestBody UserCreate body) {
        User nuevo = userService.registrar(body);
        return assembler.toModel(nuevo);
    }

    @PutMapping
    @Operation(summary = "Actualizar usuario", description = "Actualiza un usuario y devuelve el recurso actualizado con HATEOAS")
    public EntityModel<User> actualizar(@Valid @RequestBody UserUpdate body) {
        User actualizado = userService.actualizar(body);
        return assembler.toModel(actualizado);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar usuario", description = "Elimina un usuario por ID")
    public void eliminar(@PathVariable int id) {
        userService.eliminar(id);
    }
}
