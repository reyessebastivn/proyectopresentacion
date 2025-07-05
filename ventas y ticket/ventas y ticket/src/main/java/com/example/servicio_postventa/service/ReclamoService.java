package com.example.servicio_postventa.service;

import java.util.List;
import java.util.Optional;

import com.example.servicio_postventa.model.Reclamo;

public interface ReclamoService {
    List<Reclamo> listarTodos();
    Optional<Reclamo> obtenerPorId(Long id);
    Reclamo guardar(Reclamo reclamo);
    Reclamo actualizar(Long id, Reclamo reclamo);
    void eliminar(Long id);
}
