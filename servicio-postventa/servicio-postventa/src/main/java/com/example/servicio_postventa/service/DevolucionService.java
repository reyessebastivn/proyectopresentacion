package com.example.servicio_postventa.service;

import java.util.List;
import java.util.Optional;

import com.example.servicio_postventa.model.Devolucion;

public interface DevolucionService {

    List<Devolucion> listarTodas();
    Optional<Devolucion> obtenerPorId(Long id);
    Devolucion guardar(Devolucion devolucion);
    Devolucion actualizar(Long id, Devolucion devolucion);
    void eliminar(Long id);
}
