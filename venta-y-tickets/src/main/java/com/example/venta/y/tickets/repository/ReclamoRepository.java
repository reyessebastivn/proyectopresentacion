package com.example.venta.y.tickets.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.venta.y.tickets.model.Reclamo;

@Repository
public interface ReclamoRepository extends JpaRepository<Reclamo, Long> {
}
