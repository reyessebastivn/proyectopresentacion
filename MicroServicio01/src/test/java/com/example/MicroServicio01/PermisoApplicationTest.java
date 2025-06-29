package com.example.MicroServicio01;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.models.entities.Permiso;
import com.example.repositories.PermisoRepository;
import com.example.services.PermisoService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;


public class PermisoApplicationTest {
    @Mock
    private PermisoRepository permisoRepository;

    @InjectMocks
    private PermisoService permisoService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testListar() {
        Permiso permiso1 = new Permiso();
        permiso1.setId(1);
        permiso1.setNombre("PERMISO_VER");

        Permiso permiso2 = new Permiso();
        permiso2.setId(2);
        permiso2.setNombre("PERMISO_EDITAR");

        when(permisoRepository.findAll()).thenReturn(Arrays.asList(permiso1, permiso2));

        List<Permiso> resultado = permisoService.listar();

        assertEquals(2, resultado.size());
        assertEquals("PERMISO_VER", resultado.get(0).getNombre());
        assertEquals("PERMISO_EDITAR", resultado.get(1).getNombre());
    }

    @Test
    public void testGuardar() {
        Permiso permiso = new Permiso();
        permiso.setId(1);
        permiso.setNombre("PERMISO_CREAR");

        when(permisoRepository.save(permiso)).thenReturn(permiso);

        Permiso resultado = permisoService.guardar(permiso);

        assertNotNull(resultado);
        assertEquals("PERMISO_CREAR", resultado.getNombre());
        verify(permisoRepository, times(1)).save(permiso);
    }
    
}
