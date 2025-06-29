package com.example.api_perfume;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.api_perfume.models.entities.Envio;
import com.example.api_perfume.repository.EnvioRepository;
import com.example.api_perfume.services.EnvioService;

@ExtendWith(MockitoExtension.class)
class EnvioServiceTest {

    @Mock
    private EnvioRepository envioRepository;

    @InjectMocks
    private EnvioService envioService;

    private Envio crearEnvio(Long id, String destino, String estado, String fechaEnvio, String transportista) {
        Envio envio = new Envio();
        envio.setId(id);
        envio.setDestino(destino);
        envio.setEstado(estado);
        envio.setFechaEnvio(fechaEnvio);   // Ahora fecha como String
        envio.setTransportista(transportista);
        return envio;
    }

    @Test
    void testListarTodos() {
        Envio envio1 = crearEnvio(1L, "Santiago", "Pendiente", "2025-06-29", "Juan");
        Envio envio2 = crearEnvio(2L, "Valparaíso", "En camino", "2025-06-30", "Pedro");

        when(envioRepository.findAll()).thenReturn(Arrays.asList(envio1, envio2));

        List<Envio> resultado = envioService.listarTodos();

        assertEquals(2, resultado.size());
        verify(envioRepository).findAll();
    }

    @Test
    void testObtenerPorId_existente() {
        Envio envio = crearEnvio(1L, "Santiago", "Entregado", "2025-07-01", "Luis");

        when(envioRepository.findById(1L)).thenReturn(Optional.of(envio));

        Optional<Envio> resultado = envioService.obtenerPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals("Santiago", resultado.get().getDestino());
    }

    @Test
    void testObtenerPorId_noExistente() {
        when(envioRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<Envio> resultado = envioService.obtenerPorId(99L);

        assertFalse(resultado.isPresent());
    }

    @Test
    void testCrear() {
        Envio nuevo = crearEnvio(null, "Temuco", "Pendiente", "2025-07-02", "Carlos");
        Envio guardado = crearEnvio(3L, "Temuco", "Pendiente", "2025-07-02", "Carlos");

        when(envioRepository.save(nuevo)).thenReturn(guardado);

        Envio resultado = envioService.guardarEnvio(nuevo);

        assertNotNull(resultado.getId());
        assertEquals("Temuco", resultado.getDestino());
    }

    @Test
    void testActualizar_existente() {
        Envio existente = crearEnvio(1L, "Iquique", "Pendiente", "2025-07-03", "Raúl");
        Envio actualizado = crearEnvio(null, "Arica", "Entregado", "2025-07-04", "María");

        when(envioRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(envioRepository.save(any(Envio.class))).thenAnswer(invoc -> invoc.getArgument(0));

        Envio resultado = envioService.actualizar(1L, actualizado);

        assertEquals("Arica", resultado.getDestino());
        assertEquals("Entregado", resultado.getEstado());
        assertEquals("María", resultado.getTransportista());
        assertEquals("2025-07-04", resultado.getFechaEnvio());
    }

    @Test
    void testActualizar_noExistente() {
        Envio actualizado = crearEnvio(null, "La Serena", "Pendiente", "2025-07-05", "Mario");

        when(envioRepository.findById(99L)).thenReturn(Optional.empty());

        Envio resultado = envioService.actualizar(99L, actualizado);

        assertNull(resultado);
    }

    @Test
    void testEliminar() {
        doNothing().when(envioRepository).deleteById(1L);

        envioService.eliminar(1L);

        verify(envioRepository).deleteById(1L);
    }
}
