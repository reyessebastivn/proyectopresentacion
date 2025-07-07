package com.example.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.models.entities.User;
import com.example.models.requests.UserCreate;
import com.example.models.requests.UserUpdate;
import com.example.services.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/usuario")
@Tag(name = "Usuarios", description = "Controlador para gestión de usuarios")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    @Operation(summary = "Listar todos los usuarios", description = "Obtiene la lista completa de usuarios registrados")
    public List<User> obtenerTodos() {
        return userService.obtenerTodos();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener usuario por ID", description = "Obtiene un usuario específico por su ID")
    public User obtenerPorId(
            @Parameter(description = "ID del usuario", example = "1", required = true)
            @PathVariable int id) {
        return userService.obtenerPorId(id);
    }

    @PostMapping
    @Operation(summary = "Registrar nuevo usuario", description = "Registra un nuevo usuario en el sistema")
    public User registrar(
            @Parameter(description = "Datos para crear el usuario", required = true)
            @Valid @RequestBody UserCreate user) {
        return userService.registrar(user);
    }

    @PutMapping
    @Operation(summary = "Actualizar usuario", description = "Actualiza los datos de un usuario existente")
    public User actualizar(
            @Parameter(description = "Datos para actualizar el usuario", required = true)
            @Valid @RequestBody UserUpdate user) {
        return userService.actualizar(user);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar usuario", description = "Elimina un usuario existente por su ID")
    public void eliminar(
            @Parameter(description = "ID del usuario", example = "1", required = true)
            @PathVariable int id) {
        userService.eliminar(id);
    }
}
