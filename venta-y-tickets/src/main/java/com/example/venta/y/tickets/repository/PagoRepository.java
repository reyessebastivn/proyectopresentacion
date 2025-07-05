package com.example.venta.y.tickets.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.venta.y.tickets.model.Pago;
@Repository
public interface PagoRepository extends JpaRepository<Pago, Integer>  {


    List<Pago> findByIdUsuario(Long idUsuario);

    
}
