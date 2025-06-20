package com.example.api_ventas_inventario.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.api_ventas_inventario.models.Boleta;

public interface BoletaRepository extends JpaRepository<Boleta, Long>{
    
}
