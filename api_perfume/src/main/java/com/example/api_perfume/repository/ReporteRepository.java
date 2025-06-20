package com.example.api_perfume.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.api_perfume.models.entities.Reporte;

@Repository
public interface ReporteRepository extends JpaRepository<Reporte, Long> {
    List<Reporte> findByTipo(String tipo);
    List<Reporte> findBySucursal(String sucursal);
    List<Reporte> findByFechaGeneracionBetween(LocalDate desde, LocalDate hasta);
}
