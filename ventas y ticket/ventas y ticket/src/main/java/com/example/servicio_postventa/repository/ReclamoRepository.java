package com.example.servicio_postventa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.servicio_postventa.model.Reclamo;

public interface ReclamoRepository extends JpaRepository<Reclamo, Long> {
}
