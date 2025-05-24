package com.example.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public User obtenerUno(@PathVariable int id) {
        return userService.obtenerPorId(id);
    }

    @PostMapping("/")
    public User registrar(@Valid @RequestBody UserCreate body) {
        return userService.registrar(body);
    }

    @PutMapping("/")
    public User modificar(@Valid @RequestBody UserUpdate body){
        return userService.actualizar(body);
    }

      @PutMapping()
    public User actualizar(@Valid @RequestBody UserUpdate body) {
        return userService.actualizar(body);
    }

    @DeleteMapping("/{id}")
    public String eliminar(@PathVariable int id) {
        userService.eliminar(id);
        return "ok";
    }
}

