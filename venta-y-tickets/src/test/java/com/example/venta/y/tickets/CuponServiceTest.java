package com.example.venta.y.tickets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.sql.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.venta.y.tickets.model.Cupon;

import com.example.venta.y.tickets.service.CuponService;

@SpringBootTest
public class CuponServiceTest {

    @Autowired
    private CuponService cuponService;

    @BeforeEach
    public void setUp() {
        Cupon existente = cuponService.obtenerPorCodigo("TEST10");
        if (existente == null) {
            Cupon request = new Cupon();
            request.setCodigo("TEST10");
            request.setDescuento(10.0);
            request.setFechaExpiracion(Date.valueOf("2025-12-31"));
            cuponService.registrarCupon(request);
        }
    }

    @Test
    public void testBuscarPorCodigo_CuandoExiste_DeberiaRetornarCupon() {
        Cupon resultado = cuponService.obtenerPorCodigo("TEST10");
        assertNotNull(resultado);
        assertEquals("TEST10", resultado.getCodigo());
        assertEquals(10.0, resultado.getDescuento());
    }

    @Test
    public void testBuscarPorCodigo_CuandoNoExiste_DeberiaRetornarNull() {
        Cupon resultado = cuponService.obtenerPorCodigo("INEXISTENTE");
        assertNull(resultado);
    }
}
