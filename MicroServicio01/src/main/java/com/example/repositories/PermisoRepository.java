package com.example.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.models.entities.Permiso;

public interface PermisoRepository extends JpaRepository<Permiso, Long> {
    
    // Buscar permiso por nombre exacto
    Optional<Permiso> findByNombre(String nombre);
    
    // Buscar permisos que contengan una palabra en el nombre (ignora mayúsculas/minúsculas)
    List<Permiso> findByNombreContainingIgnoreCase(String nombre);

    // Listar todos los permisos ordenados por nombre ascendente
    List<Permiso> findAllByOrderByNombreAsc();
    
    // Verificar si existe un permiso por nombre
    boolean existsByNombre(String nombre);
}
