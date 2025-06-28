package com.example.api_perfume;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.api_perfume.models.entities.Sucursal;
import com.example.api_perfume.repository.SucursalRepository;
import com.example.api_perfume.services.SucursalService;

@ExtendWith(MockitoExtension.class)
class SucursalServiceTest {

    @Mock
    private SucursalRepository sucursalRepository;

    @InjectMocks
    private SucursalService sucursalService;

    private Sucursal crearSucursal(Long id, String nombre) {
        Sucursal s = new Sucursal();
        s.setId(id);
        s.setNombre(nombre);
        s.setDireccion("Dirección " + nombre);
        s.setHorarioApertura("09:00");
        s.setHorarioCierre("18:00");
        s.setPersonalAsignado("5 personas");
        s.setPoliticasLocales("Política estándar");
        return s;
    }

    @Test
    void testListarTodas() {
        Sucursal s1 = crearSucursal(1L, "Sucursal A");
        Sucursal s2 = crearSucursal(2L, "Sucursal B");

        when(sucursalRepository.findAll()).thenReturn(Arrays.asList(s1, s2));

        List<Sucursal> resultado = sucursalService.listarTodas();

        assertEquals(2, resultado.size());
        verify(sucursalRepository).findAll();
    }

    @Test
    void testObtenerPorId_existente() {
        Sucursal s = crearSucursal(1L, "Sucursal A");

        when(sucursalRepository.findById(1L)).thenReturn(Optional.of(s));

        Sucursal resultado = sucursalService.obtenerPorId(1L);

        assertNotNull(resultado);
        assertEquals("Sucursal A", resultado.getNombre());
    }

    @Test
    void testObtenerPorId_noExistente() {
        when(sucursalRepository.findById(99L)).thenReturn(Optional.empty());

        Sucursal resultado = sucursalService.obtenerPorId(99L);

        assertNull(resultado);
    }

    @Test
    void testCrear() {
        Sucursal nueva = crearSucursal(null, "Sucursal Nueva");
        Sucursal guardada = crearSucursal(3L, "Sucursal Nueva");

        when(sucursalRepository.save(nueva)).thenReturn(guardada);

        Sucursal resultado = sucursalService.crear(nueva);

        assertNotNull(resultado.getId());
        assertEquals("Sucursal Nueva", resultado.getNombre());
        verify(sucursalRepository).save(nueva);
    }

    @Test
    void testActualizar_existente() {
        Sucursal existente = crearSucursal(1L, "Sucursal Antigua");
        Sucursal datos = crearSucursal(null, "Sucursal Actualizada");

        when(sucursalRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(sucursalRepository.save(any(Sucursal.class))).thenAnswer(inv -> inv.getArgument(0));

        Sucursal resultado = sucursalService.actualizar(1L, datos);

        assertEquals("Sucursal Actualizada", resultado.getNombre());
        assertEquals("Dirección Sucursal Actualizada", resultado.getDireccion());
    }

    @Test
    void testActualizar_noExistente() {
        Sucursal datos = crearSucursal(null, "Sucursal X");

        when(sucursalRepository.findById(99L)).thenReturn(Optional.empty());

        Sucursal resultado = sucursalService.actualizar(99L, datos);

        assertNull(resultado);
    }

    @Test
    void testEliminar() {
        doNothing().when(sucursalRepository).deleteById(1L);

        sucursalService.eliminar(1L);

        verify(sucursalRepository).deleteById(1L);
    }

    @Test
    void testBuscarPorNombre() {
        Sucursal s1 = crearSucursal(1L, "Sucursal Norte");
        Sucursal s2 = crearSucursal(2L, "Sucursal Norte Alta");

        when(sucursalRepository.findByNombreContaining("Norte")).thenReturn(Arrays.asList(s1, s2));

        List<Sucursal> resultado = sucursalService.buscarPorNombre("Norte");

        assertEquals(2, resultado.size());
    }
}
