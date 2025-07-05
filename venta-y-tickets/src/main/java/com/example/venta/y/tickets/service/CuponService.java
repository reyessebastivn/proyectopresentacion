package com.example.venta.y.tickets.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.venta.y.tickets.model.Cupon;
import com.example.venta.y.tickets.repository.CuponRepository;

@Service
public class CuponService {

    private final CuponRepository cuponRepository;

    public CuponService(CuponRepository cuponRepository) {
        this.cuponRepository = cuponRepository;
    }

    public Cupon registrarCupon(Cupon cupon) {
        if (cuponRepository.findById(cupon.getCodigo()) != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El cupón ingresado ya está en uso");
        }
        cupon.setActivo(true);
        return cuponRepository.save(cupon);
    }

    public Cupon obtenerCuponId(String codigo) {
        Cupon cupon = cuponRepository.findById(codigo);
        if (cupon == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "El cupón no existe");
        }
        return cupon;
    }

    public List<Cupon> obtenerTodosLosCupones() {
        return cuponRepository.findAll();
    }

    public void eliminarCuponPorId(String codigo) {
        Cupon cupon = cuponRepository.findById(codigo);
        if (cupon == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cupón no reconocido");
        }
        cuponRepository.delete(cupon);
    }

    public Cupon validarCupon(String codigo) {
        Cupon cupon = cuponRepository.findById(codigo);
        if (cupon == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cupón inexistente");
        }
        if (!cupon.isActivo()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cupón no activado");
        }
        if (cupon.getFechaExpiracion() != null &&
            cupon.getFechaExpiracion().toLocalDate().isBefore(LocalDate.now())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cupón caducado");
        }
        return cupon;
    }
}
