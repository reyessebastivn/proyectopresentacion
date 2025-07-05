package com.example.servicio_postventa.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.servicio_postventa.model.Reclamo;
import com.example.servicio_postventa.repository.ReclamoRepository;

@Service
public class ReclamoServiceImpl implements ReclamoService {


    
    @Autowired
    private ReclamoRepository reclamoRepository;

    @Override
    public List<Reclamo> listarTodos() {
        return reclamoRepository.findAll();
    }

    @Override
    public Optional<Reclamo> obtenerPorId(Long id) {
        return reclamoRepository.findById(id);
    }

    @Override
    public Reclamo guardar(Reclamo reclamo) {
        return reclamoRepository.save(reclamo);
    }

    @Override
    public Reclamo actualizar(Long id, Reclamo nuevo) {
        return reclamoRepository.findById(id).map(reclamo -> {
            reclamo.setClienteNombre(nuevo.getClienteNombre());
            reclamo.setDescripcion(nuevo.getDescripcion());
            reclamo.setEstado(nuevo.getEstado());
            reclamo.setFecha(nuevo.getFecha());
            return reclamoRepository.save(reclamo);
        }).orElse(null);
    }

    @Override
    public void eliminar(Long id) {
        reclamoRepository.deleteById(id);
    }
    
}
