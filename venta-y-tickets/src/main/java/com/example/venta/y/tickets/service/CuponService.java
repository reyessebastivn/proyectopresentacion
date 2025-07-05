package com.example.venta.y.tickets.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.venta.y.tickets.model.Cupon;
import com.example.venta.y.tickets.repository.CuponRepository;

@Service
public class CuponService {

    @Autowired
    private CuponRepository cuponRepository;



    
    public Cupon registrarCupon(Cupon cupon) {
        try {
            Cupon cuponExistente = cuponRepository.findById(cupon.getCodigo());
            if (cuponExistente != null) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "El cupón ingresado ya está en uso!");
            }
            Cupon nuevoCupon = new Cupon();
            nuevoCupon.setCodigo(cupon.getCodigo());
            nuevoCupon.setDescuento(cupon.getDescuento());
            nuevoCupon.setActivo(true);
            nuevoCupon.setFechaExpiracion(cupon.getFechaExpiracion());
            return cuponRepository.save(nuevoCupon);
        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Algo salió mal al guardar el cupón!", e);
        }
    }




    public Cupon obtenerCuponId(String codigo) {
        Cupon cupon = cuponRepository.findById(codigo);
        if (cupon == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "El cupón ingresado no existe!");
        }
        return cupon;
    }



    public List<Cupon> obtenerTodosLosCupones() {
        return cuponRepository.findAll();
    }





    public void eliminarCuponPorId(String codigo) {
        Cupon cupon = cuponRepository.findById(codigo);
        if (cupon == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cupon no reconocido!");
        }
        cuponRepository.delete(cupon);
    }





    public Cupon validarCupon(String codigo) {
        Cupon cupon = cuponRepository.findById(codigo);
        if (cupon == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cupon inexistente!");
        }
        if (!cupon.isActivo()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cupon no activado!");
        }
        if (cupon.getFechaExpiracion() != null &&
            cupon.getFechaExpiracion().toLocalDate().isBefore(LocalDate.now())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cupon caducado!");
        }
        return cupon;
    }
}
