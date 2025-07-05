package com.example.venta.y.tickets.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.venta.y.tickets.model.Reclamo;

@Repository
public interface ReclamoRepository extends JpaRepository<Reclamo, Long> {
    List<Reclamo> findByClienteNombre(String clienteNombre);
    List<Reclamo> findByEstado(String estado);
}
