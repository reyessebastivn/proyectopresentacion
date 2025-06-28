package com.example.api_perfume;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.server.ResponseStatusException;

import com.example.api_perfume.models.ModificarPerfume;
import com.example.api_perfume.models.Perfume;
import com.example.api_perfume.repository.PerfumeRepository;
import com.example.api_perfume.services.PerfumeService;

public class PerfumeServiceTest {

    @Mock
    private PerfumeRepository perfumeRepository;

    @InjectMocks
    private PerfumeService perfumeService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testObtenerTodos() {
        Perfume p1 = new Perfume();
        p1.setId(1L);

        Perfume p2 = new Perfume();
        p2.setId(2L);

        when(perfumeRepository.findAll()).thenReturn(Arrays.asList(p1, p2));

        List<Perfume> resultado = perfumeService.obtenerTodos();

        assertEquals(2, resultado.size());
        verify(perfumeRepository, times(1)).findAll();
    }

    @Test
    public void testObtenerUno_Existente() {
        Perfume perfume = new Perfume();
        perfume.setId(1L);

        when(perfumeRepository.findById(1L)).thenReturn(Optional.of(perfume));

        Perfume resultado = perfumeService.obtenerUno(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
    }

    @Test
    public void testObtenerUno_NoExistente() {
        when(perfumeRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> {
            perfumeService.obtenerUno(1L);
        });
    }

    @Test
    public void testAgregar() {
        Perfume perfume = new Perfume();
        perfume.setMarca("Nuevo perfume");

        perfumeService.agregar(perfume);

        verify(perfumeRepository, times(1)).save(perfume);
    }

    @Test
    public void testEliminar() {
        Perfume perfume = new Perfume();
        perfume.setId(1L);

        when(perfumeRepository.findById(1L)).thenReturn(Optional.of(perfume));

        perfumeService.eliminar(1L);

        verify(perfumeRepository, times(1)).delete(perfume);
    }

    @Test
    public void testModificar() {
        Perfume perfumeExistente = new Perfume();
        perfumeExistente.setId(1L);
        perfumeExistente.setPrecio((int) 100.0);

        ModificarPerfume modificarPerfume = new ModificarPerfume();
        modificarPerfume.setPrecio((int) 150.0);

        when(perfumeRepository.findById(1L)).thenReturn(Optional.of(perfumeExistente));

        perfumeService.modificar(1L, modificarPerfume);

        assertEquals(150.0, perfumeExistente.getPrecio());
        verify(perfumeRepository, times(1)).save(perfumeExistente);
    }
}
        