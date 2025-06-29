package com.example.servicio_postventa;

import com.example.servicio_postventa.model.Devolucion;
import com.example.servicio_postventa.repository.DevolucionRepository;
import com.example.servicio_postventa.service.DevolucionService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DevolucionServiceImplTest {

    @InjectMocks
    private DevolucionService devolucionService;

    @Mock
    private DevolucionRepository devolucionRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testListarTodas() {
        // Arrange
        Devolucion d1 = new Devolucion();
        d1.setId(1L);
        d1.setIdVenta(10L);
        d1.setMotivo("Motivo 1");
        d1.setFecha(LocalDateTime.now());
        d1.setEstado("Pendiente");

        Devolucion d2 = new Devolucion();
        d2.setId(2L);
        d2.setIdVenta(20L);
        d2.setMotivo("Motivo 2");
        d2.setFecha(LocalDateTime.now());
        d2.setEstado("Completada");

        when(devolucionRepository.findAll()).thenReturn(Arrays.asList(d1, d2));

        // Act
        List<Devolucion> resultado = devolucionService.listarTodas();

        // Assert
        assertEquals(2, resultado.size());
        verify(devolucionRepository, times(1)).findAll();
    }

    @Test
    void testObtenerPorId() {
        // Arrange
        Devolucion devolucion = new Devolucion();
        devolucion.setId(1L);
        devolucion.setIdVenta(10L);
        devolucion.setMotivo("Motivo Test");
        devolucion.setFecha(LocalDateTime.now());
        devolucion.setEstado("Pendiente");

        when(devolucionRepository.findById(1L)).thenReturn(Optional.of(devolucion));

        // Act
        Optional<Devolucion> resultado = devolucionService.obtenerPorId(1L);

        // Assert
        assertTrue(resultado.isPresent());
        assertEquals("Motivo Test", resultado.get().getMotivo());
        verify(devolucionRepository, times(1)).findById(1L);
    }

    @Test
    void testGuardar() {
        // Arrange
        Devolucion devolucion = new Devolucion();
        devolucion.setIdVenta(10L);
        devolucion.setMotivo("Nuevo Motivo");
        devolucion.setFecha(LocalDateTime.now());
        devolucion.setEstado("Pendiente");

        when(devolucionRepository.save(devolucion)).thenReturn(devolucion);

        // Act
        Devolucion resultado = devolucionService.guardar(devolucion);

        // Assert
        assertNotNull(resultado);
        verify(devolucionRepository, times(1)).save(devolucion);
    }

    @Test
    void testActualizarCuandoExiste() {
        // Arrange
        Devolucion existente = new Devolucion();
        existente.setId(1L);
        existente.setIdVenta(10L);
        existente.setMotivo("Motivo Viejo");
        existente.setFecha(LocalDateTime.now());
        existente.setEstado("Pendiente");

        Devolucion nueva = new Devolucion();
        nueva.setIdVenta(20L);
        nueva.setMotivo("Motivo Nuevo");
        nueva.setFecha(LocalDateTime.now());
        nueva.setEstado("Completada");

        when(devolucionRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(devolucionRepository.save(any(Devolucion.class))).thenAnswer(i -> i.getArgument(0));

        // Act
        Devolucion resultado = devolucionService.actualizar(1L, nueva);

        // Assert
        assertNotNull(resultado);
        assertEquals("Motivo Nuevo", resultado.getMotivo());
        assertEquals(20L, resultado.getIdVenta());
        verify(devolucionRepository, times(1)).findById(1L);
        verify(devolucionRepository, times(1)).save(existente);
    }

    @Test
    void testActualizarCuandoNoExiste() {
        // Arrange
        Devolucion nueva = new Devolucion();
        nueva.setIdVenta(20L);
        nueva.setMotivo("Motivo Nuevo");
        nueva.setFecha(LocalDateTime.now());
        nueva.setEstado("Completada");

        when(devolucionRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        Devolucion resultado = devolucionService.actualizar(1L, nueva);

        // Assert
        assertNull(resultado);
        verify(devolucionRepository, times(1)).findById(1L);
        verify(devolucionRepository, times(0)).save(any());
    }

    @Test
    void testEliminar() {
        // Act
        devolucionService.eliminar(1L);

        // Assert
        verify(devolucionRepository, times(1)).deleteById(1L);
    }
}

