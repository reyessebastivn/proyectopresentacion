package com.example.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.models.entities.User;
import com.example.models.requests.UserCreate;
import com.example.models.requests.UserUpdate;
import com.example.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/usuario")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> obtenerTodos() {
        return userService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public User obtenerPorId(@PathVariable int id) {
        return userService.obtenerPorId(id);
    }

    @PostMapping
    public User registrar(@Valid @RequestBody UserCreate user) {
        return userService.registrar(user);
    }

    @PutMapping
    public User actualizar(@Valid @RequestBody UserUpdate user) {
        return userService.actualizar(user);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable int id) {
        userService.eliminar(id);
    }
}
