package com.example.api_ventas_inventario.services;

import java.util.List;
import java.util.Optional;

import com.example.api_ventas_inventario.models.Perfume;

public interface PerfumeService {
    
    List<Perfume>obtenerTodo();
    Optional<Perfume> findById(Long id);
    Perfume save(Perfume perfume);
    void deleteById(Long id);

}
