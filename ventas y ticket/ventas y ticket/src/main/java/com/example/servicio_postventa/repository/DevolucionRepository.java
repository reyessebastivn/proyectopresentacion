package com.example.servicio_postventa.repository;



import org.springframework.data.jpa.repository.JpaRepository;

import com.example.servicio_postventa.model.Devolucion;

public interface DevolucionRepository extends JpaRepository<Devolucion, Long> {
}
