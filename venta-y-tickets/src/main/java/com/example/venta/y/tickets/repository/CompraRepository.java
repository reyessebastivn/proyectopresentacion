package com.example.venta.y.tickets.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.venta.y.tickets.model.Compra;

import java.util.List;

public interface CompraRepository extends JpaRepository<Compra, Long> {
    List<Compra> findByUsuarioId(Long usuarioId);
}