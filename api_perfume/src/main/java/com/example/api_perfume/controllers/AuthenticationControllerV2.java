package com.example.api_perfume.controllers;

import com.example.api_perfume.models.entities.Perfume;
import com.example.api_perfume.models.responses.LoginResponse;
import com.example.api_perfume.services.JwtService;
import com.example.api_perfume.services.PerfumeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v2/auth")
@Tag(name = "Authentication API v2", description = "API versión 2 para autenticación y validación de tokens JWT")
public class AuthenticationControllerV2 {

    @Autowired
    private PerfumeService perfumeService;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/token")
    @Operation(
        summary = "Generar token JWT",
        description = "Permite iniciar sesión y obtener un token JWT válido para futuras peticiones",
        requestBody = @RequestBody(
                description = "Datos de login: ID y password del usuario",
                required = true,
                content = @Content(schema = @Schema(implementation = LoginResponse.class))
        ),
        responses = {
            @ApiResponse(responseCode = "200", description = "Token generado exitosamente"),
            @ApiResponse(responseCode = "401", description = "Credenciales inválidas")
        }
    )
    public LoginResponse generarToken(@Valid @org.springframework.web.bind.annotation.RequestBody LoginResponse body) {
        String token = perfumeService.intentarLogin(body.getId(), body.getPassword());
        return new LoginResponse(token);
    }

    @GetMapping("/yo")
    @Operation(
        summary = "Obtener datos del usuario autenticado",
        description = "Devuelve los datos del perfume (usuario) actualmente autenticado según el token JWT",
        security = @SecurityRequirement(name = "bearerAuth")
    )
    public Perfume obtenerMisDatos(
            @Parameter(hidden = true, description = "Token JWT en el encabezado Authorization")
            @RequestHeader("Authorization") String authHeader) {
        return jwtService.comprobarToken(authHeader);
    }
}
