package com.example.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.models.entities.Permiso;
import com.example.repositories.PermisoRepository;

@Service
public class PermisoService {

    @Autowired
    private PermisoRepository permisoRepository;

    // Listar todos los permisos
    public List<Permiso> listar() {
        return permisoRepository.findAll();
    }

    // Guardar o actualizar un permiso
    public Permiso guardar(Permiso permiso) {
        return permisoRepository.save(permiso);
    }

    // Buscar permiso por ID
    public Optional<Permiso> buscarPorId(Long id) {
        return permisoRepository.findById(id);
    }

    // Buscar permiso por nombre
    public Optional<Permiso> buscarPorNombre(String nombre) {
        return permisoRepository.findByNombre(nombre);
    }

    // Verificar si un permiso ya existe por nombre
    public boolean existePorNombre(String nombre) {
        return permisoRepository.existsByNombre(nombre);
    }

    // Eliminar un permiso por ID
    public void eliminarPorId(Long id) {
        permisoRepository.deleteById(id);
    }
}
