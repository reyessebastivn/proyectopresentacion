package com.example.servicio_postventa.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.servicio_postventa.model.Devolucion;
import com.example.servicio_postventa.repository.DevolucionRepository;

@Service
public class DevolucionServiceLmpl implements DevolucionService {

    @Autowired
    private DevolucionRepository devolucionRepository;

    @Override
    public List<Devolucion> listarTodas() {
        return devolucionRepository.findAll();
    }

    @Override
    public Optional<Devolucion> obtenerPorId(Long id) {
        return devolucionRepository.findById(id);
    }

    @Override
    public Devolucion guardar(Devolucion devolucion) {
        return devolucionRepository.save(devolucion);
    }

    @Override
    public Devolucion actualizar(Long id, Devolucion nueva) {
        return devolucionRepository.findById(id).map(devolucion -> {
            devolucion.setIdVenta(nueva.getIdVenta());
            devolucion.setMotivo(nueva.getMotivo());
            devolucion.setFecha(nueva.getFecha());
            devolucion.setEstado(nueva.getEstado());
            return devolucionRepository.save(devolucion);
        }).orElse(null);
    }

    @Override
    public void eliminar(Long id) {
        devolucionRepository.deleteById(id);
    }
}
