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
        Cupon existente = cuponService.obtenerCuponId("Prueba01");
        if (existente == null) {
            Cupon request = new Cupon();
            request.setCodigo("Prueba01");
            request.setDescuento(10.0);
            request.setFechaExpiracion(Date.valueOf("2025-12-31"));
            cuponService.registrarCupon(request);
        }
    }

    @Test
    public void testBuscarPorCodigo_CuandoExiste_DeberiaRetornarCupon() {
        Cupon resultado = cuponService.obtenerCuponId("Prueba01");
        assertNotNull(resultado);
        assertEquals("Prueba01", resultado.getCodigo());
        assertEquals(10.0, resultado.getDescuento());
    }

    @Test
    public void testBuscarPorCodigo_CuandoNoExiste_DeberiaRetornarNull() {
        Cupon resultado = cuponService.obtenerCuponId("SIN_CUPON");
        assertNull(resultado);
    }
}
