package com.example.api_perfume;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.api_perfume.models.entities.Reporte;
import com.example.api_perfume.repository.ReporteRepository;
import com.example.api_perfume.services.ReporteService;

@ExtendWith(MockitoExtension.class)
class ReporteServiceTest {

    @Mock
    private ReporteRepository reporteRepository;

    @InjectMocks
    private ReporteService reporteService;

    private Reporte crearReporte(Long id, String tipo, String sucursal, LocalDate fecha) {
        Reporte r = new Reporte();
        r.setId(id);
        r.setTipo(tipo);
        r.setSucursal(sucursal);
        r.setFechaGeneracion(fecha);
        r.setContenido("Contenido de prueba");
        return r;
    }

    @Test
    void testObtenerTodos() {
        Reporte r1 = crearReporte(1L, "Ventas", "Sucursal A", LocalDate.now());
        Reporte r2 = crearReporte(2L, "Inventario", "Sucursal B", LocalDate.now());

        when(reporteRepository.findAll()).thenReturn(Arrays.asList(r1, r2));

        List<Reporte> resultado = reporteService.obtenerTodos();

        assertEquals(2, resultado.size());
        verify(reporteRepository).findAll();
    }

    @Test
    void testObtenerPorId_existente() {
        Reporte r = crearReporte(1L, "Ventas", "Sucursal A", LocalDate.now());

        when(reporteRepository.findById(1L)).thenReturn(Optional.of(r));

        Reporte resultado = reporteService.obtenerPorId(1L);

        assertNotNull(resultado);
        assertEquals("Ventas", resultado.getTipo());
    }

    @Test
    void testObtenerPorId_noExistente() {
        when(reporteRepository.findById(99L)).thenReturn(Optional.empty());

        Reporte resultado = reporteService.obtenerPorId(99L);

        assertNull(resultado);
    }

    @Test
    void testCrear() {
        Reporte nuevo = crearReporte(null, "Estadístico", "Sucursal Z", LocalDate.now());
        Reporte guardado = crearReporte(3L, "Estadístico", "Sucursal Z", nuevo.getFechaGeneracion());

        when(reporteRepository.save(nuevo)).thenReturn(guardado);

        Reporte resultado = reporteService.crear(nuevo);

        assertNotNull(resultado.getId());
        assertEquals("Estadístico", resultado.getTipo());
        verify(reporteRepository).save(nuevo);
    }

    @Test
    void testActualizar_existente() {
        Reporte original = crearReporte(1L, "Ventas", "Sucursal A", LocalDate.now().minusDays(1));
        Reporte cambios = crearReporte(null, "Inventario", "Sucursal B", LocalDate.now());

        when(reporteRepository.findById(1L)).thenReturn(Optional.of(original));
        when(reporteRepository.save(any(Reporte.class))).thenAnswer(inv -> inv.getArgument(0));

        Reporte resultado = reporteService.actualizar(1L, cambios);

        assertEquals("Inventario", resultado.getTipo());
        assertEquals("Sucursal B", resultado.getSucursal());
    }

    @Test
    void testActualizar_noExistente() {
        Reporte cambios = crearReporte(null, "Ventas", "Sucursal A", LocalDate.now());

        when(reporteRepository.findById(99L)).thenReturn(Optional.empty());

        Reporte resultado = reporteService.actualizar(99L, cambios);

        assertNull(resultado);
    }

    @Test
    void testEliminar() {
        doNothing().when(reporteRepository).deleteById(1L);

        reporteService.eliminar(1L);

        verify(reporteRepository).deleteById(1L);
    }

    @Test
    void testBuscarPorTipo() {
        Reporte r1 = crearReporte(1L, "Ventas", "Sucursal Norte", LocalDate.now());

        when(reporteRepository.findByTipo("Ventas")).thenReturn(List.of(r1));

        List<Reporte> resultado = reporteService.buscarPorTipo("Ventas");

        assertEquals(1, resultado.size());
        assertEquals("Ventas", resultado.get(0).getTipo());
    }

    @Test
    void testBuscarPorSucursal() {
        Reporte r1 = crearReporte(1L, "Ventas", "Sucursal Centro", LocalDate.now());

        when(reporteRepository.findBySucursal("Sucursal Centro")).thenReturn(List.of(r1));

        List<Reporte> resultado = reporteService.buscarPorSucursal("Sucursal Centro");

        assertEquals(1, resultado.size());
        assertEquals("Sucursal Centro", resultado.get(0).getSucursal());
    }

    @Test
    void testBuscarPorRangoDeFecha() {
        LocalDate desde = LocalDate.of(2024, 1, 1);
        LocalDate hasta = LocalDate.of(2024, 12, 31);

        Reporte r1 = crearReporte(1L, "Ventas", "Sucursal X", LocalDate.of(2024, 5, 10));

        when(reporteRepository.findByFechaGeneracionBetween(desde, hasta)).thenReturn(List.of(r1));

        List<Reporte> resultado = reporteService.buscarPorRangoDeFecha(desde, hasta);

        assertEquals(1, resultado.size());
        assertTrue(resultado.get(0).getFechaGeneracion().isAfter(desde.minusDays(1)));
    }
}
