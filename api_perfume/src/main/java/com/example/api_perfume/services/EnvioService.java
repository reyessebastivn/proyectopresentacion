package com.example.api_perfume.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.api_perfume.models.entities.Envio;
import com.example.api_perfume.repository.EnvioRepository;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@Service
public class EnvioService {

    @Autowired
    private EnvioRepository envioRepository;

    public List<Envio> listarTodos() {
        return envioRepository.findAll();
    }

    public Optional<Envio> obtenerPorId(Long id) {
        return envioRepository.findById(id);
    }

   public Envio guardarEnvio(Envio envio) {
    return envioRepository.save(envio);
    }


    public Envio actualizar(Long id, Envio envioActualizado) {
        return envioRepository.findById(id)
            .map(envio -> {
                envio.setDestino(envioActualizado.getDestino());
                envio.setEstado(envioActualizado.getEstado());
                envio.setFechaEnvio(envioActualizado.getFechaEnvio());
                envio.setTransportista(envioActualizado.getTransportista());
                return envioRepository.save(envio);
            }).orElse(null);
    }

    public void eliminar(Long id) {
        envioRepository.deleteById(id);
    }
}
