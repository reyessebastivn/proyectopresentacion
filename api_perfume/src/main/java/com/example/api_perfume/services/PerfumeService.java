package com.example.api_perfume.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.security.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.api_perfume.models.ModificarPerfume;
import com.example.api_perfume.models.Perfume;
import com.example.api_perfume.repository.PerfumeRepository;

import jakarta.validation.Valid;

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

    public String hashearPassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password); 
    }

    public boolean comprobarPassword(String hash,String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(password,hash); 
    }

    public Perfume save(Perfume perfume) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }

    public Optional<Perfume> findById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    public String intentarLogin(Object id, Object password) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'intentarLogin'");
    }
}
