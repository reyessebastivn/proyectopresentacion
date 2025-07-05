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

    public List<Cupon> obtenerTodosCupones() {
        return cuponRepository.findAll();
    }

    public Cupon obtenerPorCodigo(String codigo){
        return cuponRepository.findByCodigo(codigo);
        
    }

    public Cupon registrarCupon(Cupon cupon) {
    
        Cupon nuevoCupon = new Cupon();
        nuevoCupon.setCodigo(cupon.getCodigo());
        nuevoCupon.setDescuento(cupon.getDescuento());
        nuevoCupon.setActivo(true);
        nuevoCupon.setFechaExpiracion(cupon.getFechaExpiracion());

        return cuponRepository.save(nuevoCupon);
}


    public Cupon validarCupon(String codigo) {
        Cupon cupon = cuponRepository.findByCodigo(codigo);

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

    
    public void eliminarCuponPorCodigo(String codigo) {
        Cupon cupon = cuponRepository.findByCodigo(codigo);
        
        if (cupon == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cupon no reconocido!");
        }
        
        cuponRepository.delete(cupon);  
    }

  
}
