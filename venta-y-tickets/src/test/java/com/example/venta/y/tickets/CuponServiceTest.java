package com.example.venta.y.tickets;


import com.example.venta.y.tickets.model.Cupon;
import com.example.venta.y.tickets.repository.CuponRepository;
import com.example.venta.y.tickets.service.CuponService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CuponServiceTest {

    private CuponRepository cuponRepository;
    private CuponService cuponService;

    @BeforeEach
    void setUp() {
        cuponRepository = mock(CuponRepository.class);
        cuponService = new CuponService(cuponRepository);
    }

    @Test
    void registrarCupon_deberiaGuardarNuevoCupon() {
        Cupon cupon = new Cupon();
        cupon.setCodigo("TEST123");
        cupon.setDescuento(20);
        cupon.setActivo(true);

        when(cuponRepository.findByCodigo("TEST123")).thenReturn(null);
        when(cuponRepository.save(cupon)).thenReturn(cupon);

        Cupon resultado = cuponService.registrarCupon(cupon);

        assertNotNull(resultado);
        assertTrue(resultado.isActivo());
        verify(cuponRepository).save(cupon);
    }

    @Test
    void registrarCupon_deberiaLanzarExcepcionSiExiste() {
        Cupon cupon = new Cupon();
        cupon.setCodigo("DUPLICADO");

        when(cuponRepository.findByCodigo("DUPLICADO")).thenReturn(new Cupon());

        assertThrows(ResponseStatusException.class, () -> cuponService.registrarCupon(cupon));
    }

    @Test
    void validarCupon_deberiaLanzarSiCaducado() {
        Cupon cupon = new Cupon();
        cupon.setCodigo("EXPIRO");
        cupon.setActivo(true);
        cupon.setFechaExpiracion(Date.valueOf(LocalDate.now().minusDays(1)));

        when(cuponRepository.findByCodigo("EXPIRO")).thenReturn(cupon);

        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> cuponService.validarCupon("EXPIRO"));
        assertEquals("Cupón caducado", ex.getReason());
    }

    @Test
    void obtenerTodosLosCupones_deberiaDevolverLista() {
        when(cuponRepository.findAll()).thenReturn(Collections.singletonList(new Cupon()));
        List<Cupon> lista = cuponService.obtenerTodosLosCupones();
        assertFalse(lista.isEmpty());
    }

    @Test
    void eliminarCuponPorId_deberiaEliminar() {
        Cupon cupon = new Cupon();
        cupon.setCodigo("ELIMINAR");

        when(cuponRepository.findByCodigo("ELIMINAR")).thenReturn(cupon);

        cuponService.eliminarCuponPorId("ELIMINAR");

        verify(cuponRepository).delete(cupon);
    }
}
