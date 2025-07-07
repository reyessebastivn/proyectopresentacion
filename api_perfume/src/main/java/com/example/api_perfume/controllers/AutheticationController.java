package com.example.api_perfume.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.api_perfume.models.entities.Perfume;
import com.example.api_perfume.models.responses.LoginResponse;
import com.example.api_perfume.services.JwtService;
import com.example.api_perfume.services.PerfumeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

@RestController
@RequestMapping("auth")
public class AutheticationController {

    @Autowired
    private PerfumeService perfumeService;

    @Autowired
    private JwtService jwtService;

    @PostMapping("token")
    @Operation(summary = "Generar token JWT",
               description = "Permite iniciar sesión y obtener un token JWT válido para futuras peticiones")
    public LoginResponse postMethodName(@RequestBody @Valid LoginResponse body) {
        String token = perfumeService.intentarLogin(body.getId(), body.getPassword());
        return new LoginResponse(token);
    }

    @GetMapping("yo")
    @Operation(summary = "Obtener información propia", 
               description = "Devuelve los datos del usuario autenticado según el token JWT enviado")
    @SecurityRequirement(name = "bearerAuth")
    public Perfume misDatos(@Parameter(hidden = true) @RequestHeader("Authorization") String authHeader) {
        Perfume perfume = jwtService.comprobarToken(authHeader);
        return perfume;
    }
}
