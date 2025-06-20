package com.example.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.models.entities.ConfigPermisos;

public interface ConfigPermisosRepository extends JpaRepository<ConfigPermisos, Long>{

    List<ConfigPermisos> findByRol(String rol);
}