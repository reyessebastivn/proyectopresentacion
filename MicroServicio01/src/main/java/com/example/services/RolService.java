package com.example.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.models.entities.Rol;
import com.example.repositories.RolRepository;

@Service
public class RolService {

    @Autowired
    private RolRepository rolRepository;

    // Listar todos los roles
    public List<Rol> listar() {
        return rolRepository.findAll();
    }

    // Guardar o actualizar un rol
    public Rol guardar(Rol rol) {
        return rolRepository.save(rol);
    }

    // Buscar rol por ID
    public Optional<Rol> buscarPorId(Long id) {
        return rolRepository.findById(id);
    }

    // Buscar rol por nombre
    public Optional<Rol> buscarPorNombre(String nombre) {
        return rolRepository.findByNombre(nombre);
    }

    // Verificar existencia por nombre
    public boolean existePorNombre(String nombre) {
        return rolRepository.existsByNombre(nombre);
    }

    // Eliminar rol por ID
    public void eliminarPorId(Long id) {
        rolRepository.deleteById(id);
    }
}
