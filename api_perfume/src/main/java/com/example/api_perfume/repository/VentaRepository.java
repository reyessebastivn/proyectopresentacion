package com.example.api_perfume.repository;

import com.example.api_perfume.models.entities.Venta;
import org.springframework.data.jpa.repository.JpaRepository;

// Repositorio para acceder a la base de datos (tabla ventas)
public interface VentaRepository extends JpaRepository<Venta, Long> {
}