package com.example.api_perfume.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.api_perfume.models.entities.Sucursal;
import com.example.api_perfume.repository.SucursalRepository;

@Service
public class SucursalService {

    @Autowired
    private SucursalRepository sucursalRepository;

    public List<Sucursal> listarTodas() {
        return sucursalRepository.findAll();
    }

    public Sucursal obtenerPorId(Long id) {
        return sucursalRepository.findById(id).orElse(null);
    }

    public Sucursal crear(Sucursal sucursal) {
        return sucursalRepository.save(sucursal);
    }

    public Sucursal actualizar(Long id, Sucursal datos) {
        Sucursal sucursal = sucursalRepository.findById(id).orElse(null);
        if (sucursal != null) {
            sucursal.setNombre(datos.getNombre());
            sucursal.setDireccion(datos.getDireccion());
            sucursal.setHorarioApertura(datos.getHorarioApertura());
            sucursal.setHorarioCierre(datos.getHorarioCierre());
            sucursal.setPersonalAsignado(datos.getPersonalAsignado());
            sucursal.setPoliticasLocales(datos.getPoliticasLocales());
            return sucursalRepository.save(sucursal);
        }
        return null;
    }

    public void eliminar(Long id) {
        sucursalRepository.deleteById(id);
    }

    public List<Sucursal> buscarPorNombre(String nombre) {
        return sucursalRepository.findByNombreContaining(nombre);
    }
    
}
