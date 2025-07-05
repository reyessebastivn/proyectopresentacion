package com.example.api_perfume.config;

import lombok.Data;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UsuarioServiceClient {

    // URL base del Microservicio de Usuarios (Microservicio01)
    private final String urlApiUsuarios = "http://localhost:8081/api/usuarios/";  // Cambia el puerto si es necesario

    // Método que obtiene los datos del usuario por ID
    public UsuarioResponse obtenerUsuarioPorId(Long idUsuario) throws Exception {
        RestTemplate restTemplate = new RestTemplate();

        // Hace la petición al otro microservicio
        UsuarioResponse usuario = restTemplate.getForObject(urlApiUsuarios + idUsuario, UsuarioResponse.class);

        if (usuario == null) {
            throw new Exception("Usuario no encontrado en el Microservicio01 con id: " + idUsuario);
        }

        return usuario;
    }

    // Clase interna para mapear la respuesta del microservicio de usuarios
    @Data
    public static class UsuarioResponse {
        private Long id;
        private String nombre;
        private String rut;
        
    }
}
