package com.example.api_perfume.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.api_perfume.models.entities.Envio;

public interface EnvioRepository extends JpaRepository<Envio, Long> {
}
