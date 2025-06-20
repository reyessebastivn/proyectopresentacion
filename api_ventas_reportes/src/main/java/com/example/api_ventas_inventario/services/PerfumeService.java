package com.example.api_ventas_inventario.services;

import java.util.List;
import java.util.Optional;

import com.example.api_ventas_inventario.models.Perfume;

public interface PerfumeService {
    
    List<Perfume>getAllPerfumes();
    Optional<Perfume> getPerfumeById(Long id);
    Perfume savePerfume(Perfume perfume);
    void deletePerfume(Long id);

}
