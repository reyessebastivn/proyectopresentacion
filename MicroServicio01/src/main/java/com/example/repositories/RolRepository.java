package com.example.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.models.entities.Rol;

public interface RolRepository extends JpaRepository<Rol, Long> {
    
    // Buscar rol por nombre exacto
    Optional<Rol> findByNombre(String nombre);
    
    // Buscar roles que contengan una palabra en el nombre (ignore case)
    List<Rol> findByNombreContainingIgnoreCase(String nombre);
    
    // Listar todos los roles ordenados por nombre ascendente
    List<Rol> findAllByOrderByNombreAsc();
    
    // Verificar si existe un rol por nombre
    boolean existsByNombre(String nombre);
}
