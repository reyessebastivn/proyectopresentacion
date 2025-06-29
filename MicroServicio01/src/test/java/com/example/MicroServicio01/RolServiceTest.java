package com.example.MicroServicio01;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.example.models.entities.Rol;
import com.example.repositories.RolRepository;
import com.example.services.RolService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;
import java.util.List;

public class RolServiceTest {

    @Mock
    private RolRepository rolRepository;

    @InjectMocks
    private RolService rolService;

    @Test
    public void testListar() {
        Rol rol1 = new Rol();
        rol1.setNombre("ADMIN");
        ReflectionTestUtils.setField(rol1, "id", 1L);

        Rol rol2 = new Rol();
        rol2.setNombre("USER");
        ReflectionTestUtils.setField(rol2, "id", 2L);

        when(rolRepository.findAll()).thenReturn(Arrays.asList(rol1, rol2));

        List<Rol> resultado = rolService.listar();

        assertEquals(2, resultado.size());
        assertEquals("ADMIN", resultado.get(0).getNombre());
        assertEquals("USER", resultado.get(1).getNombre());
    }
}
