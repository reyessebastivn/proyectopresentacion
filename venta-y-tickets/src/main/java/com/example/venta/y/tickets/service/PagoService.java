package com.example.venta.y.tickets.service;


import java.time.LocalDate;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.venta.y.tickets.model.Pago;
import com.example.venta.y.tickets.repository.PagoRepository;

@Service
public class PagoService {

    private final PagoRepository pagoRepository;

    public PagoService(PagoRepository pagoRepository) {
        this.pagoRepository = pagoRepository;
    }

    public List<Pago> obtenerTodosLosPagos() {
        return pagoRepository.findAll();
    }

    public List<Pago> obtenerPagosPorIdUsuario(Long idUsuario) {
        List<Pago> pagos = pagoRepository.findByIdUsuario(idUsuario);
        if (pagos == null || pagos.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
             "No se encontraron pagos para el usuario con ID: " + idUsuario);
        }
        return pagos;
    }

    public Pago guardarPago(Long userId, Long perfumeId, int precio, LocalDate fechaPago, String codigoCupon, String idTransBK) {
        try {
            Pago pago = new Pago();
            pago.setIdUsuario(userId);
            pago.setIdPerfume(perfumeId);
            pago.setPrecio(precio);
            pago.setFechaPago(fechaPago != null ? fechaPago : LocalDate.now());
            pago.setCodigoCupon(codigoCupon);
            pago.setIdTransBank(idTransBK);

            return pagoRepository.save(pago);

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
             "Error al registrar el pago: " + e.getMessage(), e);
        }
    }
}
