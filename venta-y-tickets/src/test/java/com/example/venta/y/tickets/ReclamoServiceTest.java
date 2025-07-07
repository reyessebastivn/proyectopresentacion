package com.example.venta.y.tickets;

import com.example.venta.y.tickets.model.Reclamo;
import com.example.venta.y.tickets.repository.ReclamoRepository;
import com.example.venta.y.tickets.service.ReclamoService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReclamoServiceTest {

    private ReclamoRepository reclamoRepository;
    private ReclamoService reclamoService;

    @BeforeEach
    void setUp() {
        reclamoRepository = mock(ReclamoRepository.class);
        reclamoService = new ReclamoService(reclamoRepository);
    }

    @Test
    void obtenerPorId_deberiaDevolverReclamo() {
        Reclamo reclamo = new Reclamo();
        reclamo.setId(1L);

        when(reclamoRepository.findById(1L)).thenReturn(Optional.of(reclamo));

        Reclamo resultado = reclamoService.obtenerPorId(1L);

        assertNotNull(resultado);
    }

    @Test
    void obtenerPorId_deberiaLanzarSiNoExiste() {
        when(reclamoRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> reclamoService.obtenerPorId(99L));
    }

    @Test
    void guardar_deberiaGuardar() {
        Reclamo reclamo = new Reclamo();
        when(reclamoRepository.save(reclamo)).thenReturn(reclamo);

        Reclamo resultado = reclamoService.guardar(reclamo);

        assertNotNull(resultado);
        verify(reclamoRepository).save(reclamo);
    }

    @Test
    void eliminar_deberiaEliminar() {
        when(reclamoRepository.existsById(1L)).thenReturn(true);

        reclamoService.eliminar(1L);

        verify(reclamoRepository).deleteById(1L);
    }

    @Test
    void eliminar_deberiaLanzarSiNoExiste() {
        when(reclamoRepository.existsById(99L)).thenReturn(false);

        assertThrows(ResponseStatusException.class, () -> reclamoService.eliminar(99L));
    }
}
