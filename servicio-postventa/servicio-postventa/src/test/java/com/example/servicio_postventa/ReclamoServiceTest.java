package com.example.servicio_postventa;

import com.example.servicio_postventa.controller.ReclamoControllerV2;
import com.example.servicio_postventa.model.Reclamo;
import com.example.servicio_postventa.service.ReclamoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ReclamoControllerV2.class)
class ReclamoControllerV2Test {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReclamoService reclamoService;

    @Autowired
    private ObjectMapper objectMapper;

    private Reclamo reclamo;

    @BeforeEach
    void setUp() {
        reclamo = new Reclamo();
        reclamo.setId(1L);
        reclamo.setClienteNombre("Juan Pérez");
        reclamo.setDescripcion("Producto defectuoso");
        reclamo.setEstado("Pendiente");
        reclamo.setFecha(LocalDateTime.now());
    }

    @Test
    void testListar() throws Exception {
        when(reclamoService.listarTodos()).thenReturn(Arrays.asList(reclamo));

        mockMvc.perform(get("/v2/reclamos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].clienteNombre").value("Juan Pérez"));

        verify(reclamoService, times(1)).listarTodos();
    }

    @Test
    void testObtenerPorIdExistente() throws Exception {
        when(reclamoService.obtenerPorId(1L)).thenReturn(Optional.of(reclamo));

        mockMvc.perform(get("/v2/reclamos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.clienteNombre").value("Juan Pérez"));

        verify(reclamoService, times(1)).obtenerPorId(1L);
    }

    @Test
    void testObtenerPorIdNoExistente() throws Exception {
        when(reclamoService.obtenerPorId(2L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/v2/reclamos/2"))
                .andExpect(status().isNotFound());

        verify(reclamoService, times(1)).obtenerPorId(2L);
    }

    @Test
    void testCrear() throws Exception {
        when(reclamoService.guardar(any(Reclamo.class))).thenReturn(reclamo);

        mockMvc.perform(post("/v2/reclamos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reclamo)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.clienteNombre").value("Juan Pérez"));

        verify(reclamoService, times(1)).guardar(any(Reclamo.class));
    }

    @Test
    void testActualizarExistente() throws Exception {
        when(reclamoService.actualizar(eq(1L), any(Reclamo.class))).thenReturn(reclamo);

        mockMvc.perform(put("/v2/reclamos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reclamo)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.clienteNombre").value("Juan Pérez"));

        verify(reclamoService, times(1)).actualizar(eq(1L), any(Reclamo.class));
    }

    @Test
    void testActualizarNoExistente() throws Exception {
        when(reclamoService.actualizar(eq(2L), any(Reclamo.class))).thenReturn(null);

        mockMvc.perform(put("/v2/reclamos/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reclamo)))
                .andExpect(status().isNotFound());

        verify(reclamoService, times(1)).actualizar(eq(2L), any(Reclamo.class));
    }

    @Test
    void testEliminar() throws Exception {
        doNothing().when(reclamoService).eliminar(1L);

        mockMvc.perform(delete("/v2/reclamos/1"))
                .andExpect(status().isOk());

        verify(reclamoService, times(1)).eliminar(1L);
    }
}
