package com.example.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.models.entities.ConfigPermisos;
import com.example.repositories.ConfigPermisosRepository;

@Service
public class ConfigPermisosService {

    @Autowired
    private ConfigPermisosRepository configpermisosrepository;

    public List<ConfigPermisos> obtenerTodos(){
        return configpermisosrepository.findAll();
    }
    public ConfigPermisos obtenerPorId(Long id) {
        return configpermisosrepository.findById(id).orElse(null);
    }

    public ConfigPermisos crear(ConfigPermisos permiso) {
        return configpermisosrepository.save(permiso);
    }

    public ConfigPermisos actualizar(Long id, ConfigPermisos permisoActualizado) {
        ConfigPermisos permiso = configpermisosrepository.findById(id).orElse(null);
        if (permiso != null) {
            permiso.setNombreModulo(permisoActualizado.getNombreModulo());
            permiso.setFuncion(permisoActualizado.getFuncion());
            permiso.setRol(permisoActualizado.getRol());
            return configpermisosrepository.save(permiso);
        }
        return null;
    }

    public void eliminar(Long id) {
        configpermisosrepository.deleteById(id);
    }

    public List<ConfigPermisos> obtenerPorRol(String rol) {
        return configpermisosrepository.findByRol(rol);
    }
    
}
