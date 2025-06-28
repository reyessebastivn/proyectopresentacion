package com.example.api_perfume.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.api_perfume.models.Perfume;
import com.example.api_perfume.models.responses.LoginResponse;
import com.example.api_perfume.services.JwtService;
import com.example.api_perfume.services.PerfumeService;

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
    public LoginResponse postMethodName(@RequestBody @Valid LoginResponse body) {

        String token = perfumeService.intentarLogin(body.getId(), body.getPassword());

        return new LoginResponse(token);
    }

    @GetMapping("yo")
    @SecurityRequirement(name = "bearerAuth")
    public Perfume misDatos(@Parameter(hidden = true) @RequestHeader("Authorization") String authHeader) {
        Perfume perfume = jwtService.comprobarToken(authHeader);
        return perfume;
    }
    
}
