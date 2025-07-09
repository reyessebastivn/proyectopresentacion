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

import com.example.models.entities.Rol;
import com.example.models.entities.User;
import com.example.models.requests.LoginRequest;
import com.example.models.requests.UserCreate;
import com.example.models.requests.UserUpdate;
import com.example.repositories.RolRepository;
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
            // Buscar el rol COMPRADOR
            Rol rolComprador = rolRepository.findByNombre("USUARIO_CLIENTE")
                    .orElseThrow(
                            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Rol USUARIO_CLIENTE encontrado"));

            User nuevoUsuario = new User();
            nuevoUsuario.setFechaCreacion(LocalDateTime.now());
            nuevoUsuario.setActivo(true);
            nuevoUsuario.setNombre(usuario.getNombre());
            nuevoUsuario.setEmail(usuario.getEmail());
            nuevoUsuario.setPassword(hashearPassword(usuario.getPassword()));
            nuevoUsuario.setTelefono(usuario.getTelefono());
            nuevoUsuario.setRol(rolComprador); // Asignar el rol automáticamente

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

    public User login(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciales inválidas"));

        if (!encoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciales inválidas");
        }

        return user;
    }

    @Autowired
    private RolRepository rolRepository;

    public User asignarRol(int userId, long rolId) {
        User usuario = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

        Rol rol = rolRepository.findById(rolId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Rol no encontrado"));

        usuario.setRol(rol);
        return userRepository.save(usuario);
    }

}
