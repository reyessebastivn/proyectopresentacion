package com.example.venta.y.tickets.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.venta.y.tickets.model.Cupon;

@Repository
public interface CuponRepository extends JpaRepository<Cupon, Long> {
    Cupon findByCodigo(String codigo);
}
