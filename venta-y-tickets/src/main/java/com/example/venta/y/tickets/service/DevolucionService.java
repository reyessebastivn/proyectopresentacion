package com.example.venta.y.tickets.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.venta.y.tickets.model.Devolucion;
import com.example.venta.y.tickets.repository.DevolucionRepository;

import java.util.List;

@Service
public class DevolucionService {

    @Autowired
    private DevolucionRepository devolucionRepository;

    public List<Devolucion> obtenerTodas() {
        return devolucionRepository.findAll();
    }

    public Devolucion obtenerPorId(Long id) {
        return devolucionRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Devolución no encontrada"));
    }

    public List<Devolucion> obtenerPorIdVenta(Long idVenta) {
        return devolucionRepository.findByIdVenta(idVenta);
    }

    public Devolucion guardar(Devolucion devolucion) {
        return devolucionRepository.save(devolucion);
    }

    public void eliminar(Long id) {
        if (!devolucionRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Devolución no encontrada");
        }
        devolucionRepository.deleteById(id);
    }
}
