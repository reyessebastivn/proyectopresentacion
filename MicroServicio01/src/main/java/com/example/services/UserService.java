package com.example.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.models.entities.User;
import com.example.models.requests.UserCreate;
import com.example.models.requests.UserUpdate;
import com.example.repositories.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public List<User> obtenerTodos() {
        return userRepository.findAll();
    }

    public User obtenerPorId(int id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));
    }

    public User registrar(UserCreate usuario) {
        if (userRepository.existsByEmail(usuario.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El email ya está registrado");
        }

        try {
            User nuevoUsuario = new User();
            nuevoUsuario.setFechaCreacion(LocalDateTime.now()); // Ahora usando LocalDateTime
            nuevoUsuario.setActivo(true);
            nuevoUsuario.setNombre(usuario.getNombre());
            nuevoUsuario.setEmail(usuario.getEmail());
            nuevoUsuario.setPassword(hashearPassword(usuario.getPassword()));
            nuevoUsuario.setTelefono(usuario.getTelefono());

            return userRepository.save(nuevoUsuario);

        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error al registrar el usuario");
        }
    }

    public User actualizar(UserUpdate body) {
        User usuario = userRepository.findById(body.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

        if (body.getNombre() != null) {
            usuario.setNombre(body.getNombre());
        }

        if (body.getTelefono() != null) {
            usuario.setTelefono(body.getTelefono());
        }

        if (body.getPassword() != null) {
            usuario.setPassword(hashearPassword(body.getPassword()));
        }

        return userRepository.save(usuario);
    }

    public void eliminar(int id) {
        User usuario = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));
        userRepository.delete(usuario);
    }

    public String hashearPassword(String password) {
        return encoder.encode(password);
    }
}
