package com.example.venta.y.tickets.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class Config {

    @Bean
    public WebClient webClientUsuarios() {
        return WebClient.builder().baseUrl("http://localhost:8081/api/usuarios").build();
    }

    @Bean
    public WebClient webClientPerfumes() {
        return WebClient.builder().baseUrl("http://localhost:8082/api/perfumes").build();
    }
}
