package com.example.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.models.entities.User;
import com.example.models.requests.UserCreate;
import com.example.models.requests.UserUpdate;
import com.example.repositories.UserRepository;

import jakarta.validation.Valid;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> obtenerTodos(){
        return userRepository.findAll();
    }

      public User obtenerPorId(int id){
        return userRepository.findById(id).orElse(null);
    }
    
    public User registrar(UserCreate usuario){
        try {
            User nuevoUsuario = new User();

            nuevoUsuario.setFechaCreacion(new Date());
            nuevoUsuario.setActivo(true);

            nuevoUsuario.setNombre(usuario.getNombre());
            nuevoUsuario.setEmail(usuario.getEmail());
            nuevoUsuario.setPassword(hashearPassword(usuario.getPassword()));
            nuevoUsuario.setTelefono(usuario.getTelefono());
          
            return userRepository.save(nuevoUsuario);
            
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"usuario registrado");
        }
    }
    public String hashearPassword(String password){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    } 


    public User actualizar(UserUpdate body){
        User usuario = userRepository.findById(body.getId()).orElse(null);
        if(usuario == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"no encontrado");
        }
        if(body.getNombre() != null){
            usuario.setNombre(body.getNombre());
        }
        if(body.getTelefono()!= null){
            usuario.setTelefono(body.getTelefono());
        }
        if(body.getPassword() !=null){
            usuario.setPassword(body.getPassword());
        }
        return userRepository.save(usuario);
    }

    public void eliminar(int id) {
    User usuario = userRepository.findById(id).orElse(null);
    if (usuario == null) {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado");
    }
    userRepository.delete(usuario);
}
}


