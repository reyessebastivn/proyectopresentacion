package com.example.venta.y.tickets;

import com.example.venta.y.tickets.model.Devolucion;
import com.example.venta.y.tickets.repository.DevolucionRepository;
import com.example.venta.y.tickets.service.DevolucionService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DevolucionServiceTest {

    private DevolucionRepository devolucionRepository;
    private DevolucionService devolucionService;

    @BeforeEach
    void setUp() {
        devolucionRepository = mock(DevolucionRepository.class);
        devolucionService = new DevolucionService(devolucionRepository);
    }

    @Test
    void obtenerPorId_deberiaDevolverDevolucion() {
        Devolucion devolucion = new Devolucion();
        devolucion.setId(1L);

        when(devolucionRepository.findById(1L)).thenReturn(Optional.of(devolucion));

        Devolucion resultado = devolucionService.obtenerPorId(1L);

        assertNotNull(resultado);
    }

    @Test
    void obtenerPorId_deberiaLanzarSiNoExiste() {
        when(devolucionRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> devolucionService.obtenerPorId(99L));
    }

    @Test
    void guardar_deberiaGuardar() {
        Devolucion devolucion = new Devolucion();
        when(devolucionRepository.save(devolucion)).thenReturn(devolucion);

        Devolucion resultado = devolucionService.guardar(devolucion);

        assertNotNull(resultado);
        verify(devolucionRepository).save(devolucion);
    }

    @Test
    void eliminar_deberiaEliminar() {
        when(devolucionRepository.existsById(1L)).thenReturn(true);

        devolucionService.eliminar(1L);

        verify(devolucionRepository).deleteById(1L);
    }

    @Test
    void eliminar_deberiaLanzarSiNoExiste() {
        when(devolucionRepository.existsById(99L)).thenReturn(false);

        assertThrows(ResponseStatusException.class, () -> devolucionService.eliminar(99L));
    }
}
