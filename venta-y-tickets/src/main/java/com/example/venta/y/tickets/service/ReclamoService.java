package com.example.venta.y.tickets.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.venta.y.tickets.model.Reclamo;
import com.example.venta.y.tickets.repository.ReclamoRepository;

@Service
public class ReclamoService {

    @Autowired
    private ReclamoRepository reclamoRepository;

    public List<Reclamo> obtenerTodos() {
        return reclamoRepository.findAll();
    }

    public Reclamo obtenerPorId(Long id) {
        return reclamoRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Reclamo no encontrado"));
    }

    public List<Reclamo> obtenerPorClienteNombre(String clienteNombre) {
        return reclamoRepository.findByClienteNombre(clienteNombre);
    }

    public List<Reclamo> obtenerPorEstado(String estado) {
        return reclamoRepository.findByEstado(estado);
    }

    public Reclamo guardar(Reclamo reclamo) {
        return reclamoRepository.save(reclamo);
    }

    public void eliminar(Long id) {
        if (!reclamoRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Reclamo no encontrado");
        }
        reclamoRepository.deleteById(id);
    }
}
