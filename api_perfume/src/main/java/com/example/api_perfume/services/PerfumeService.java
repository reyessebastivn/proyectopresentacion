package com.example.api_perfume.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.api_perfume.models.ModificarPerfume;
import com.example.api_perfume.models.Perfume;
import com.example.api_perfume.repository.PerfumeRepository;

@Service
public class PerfumeService {

    @Autowired
    private PerfumeRepository repo;

    public List<Perfume> obtenerTodos() {
        return repo.findAll();
    }

    public Perfume obtenerUno(Long id) {
        return repo.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Perfume no encontrado"));
    }

    public void agregar(Perfume perfume) {
        // No le pongas ID manual, JPA lo genera automáticamente
        repo.save(perfume);
    }

    public void eliminar(Long id) {
        Perfume p = obtenerUno(id);
        repo.delete(p);
    }

    public void modificar(Long id, ModificarPerfume modificarperfume) {
        Perfume p = obtenerUno(id);
        p.setPrecio(modificarperfume.getPrecio());
        repo.save(p); // Guarda los cambios en la base de datos
    }
}
