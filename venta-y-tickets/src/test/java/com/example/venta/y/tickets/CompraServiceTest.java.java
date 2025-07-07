package com.example.venta.y.tickets;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.venta.y.tickets.model.Compra;
import com.example.venta.y.tickets.model.dto.PerfumeDTO;
import com.example.venta.y.tickets.model.dto.UserDTO;
import com.example.venta.y.tickets.model.response.CompraResponse;
import com.example.venta.y.tickets.repository.CompraRepository;
import com.example.venta.y.tickets.service.CompraService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersUriSpec;
import org.springframework.web.reactive.function.client.WebClient.ResponseSpec;
import reactor.core.publisher.Mono;

import java.util.List;

class CompraServiceTest {

    /*@Mock
    private CompraRepository compraRepository;

    @Mock
    private WebClient webClientUsuarios;

    @Mock
    private WebClient webClientPerfumes;

    @Mock
    private RequestHeadersUriSpec<?> uriSpecMockUsuarios;

    @Mock
    private RequestHeadersUriSpec<?> uriSpecMockPerfumes;

    @Mock
    private WebClient.ResponseSpec responseSpecUsuarios;

    @Mock
    private WebClient.ResponseSpec responseSpecPerfumes;

    private CompraService compraService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        compraService = new CompraService(compraRepository, webClientUsuarios, webClientPerfumes);
    }

    @Test
    void testRegistrarCompra() {
        // Datos de prueba
        Compra compra = new Compra();
        compra.setUsuarioId(1L);
        compra.setPerfumeId(1L);
        compra.setCantidad(2);
        compra.setTotal(200.0);

        UserDTO userDto = new UserDTO();
        userDto.setId(1L);
        userDto.setName("Pablo");

        PerfumeDTO perfumeMock = new PerfumeDTO();
        perfumeMock.setNombre("Hugo Boss");
        perfumeMock.setPrecio(100.0);

        // Mock WebClient para usuarios
        when(webClientUsuarios.get()).thenReturn(uriSpecMockUsuarios);
        when(uriSpecMockUsuarios.uri("/{id}", 1L)).thenReturn(uriSpecMockUsuarios);
        when(uriSpecMockUsuarios.retrieve()).thenReturn(responseSpecUsuarios);
        when(responseSpecUsuarios.bodyToMono(UserDTO.class)).thenReturn(Mono.just(userDto));

        // Mock WebClient para perfumes
        when(webClientPerfumes.get()).thenReturn(uriSpecMockPerfumes);
        when(uriSpecMockPerfumes.uri("/{id}", 1L)).thenReturn(uriSpecMockPerfumes);
        when(uriSpecMockPerfumes.retrieve()).thenReturn(responseSpecPerfumes);
        when(responseSpecPerfumes.bodyToMono(PerfumeDTO.class)).thenReturn(Mono.just(perfumeMock));

        // Ejecutar
        CompraResponse response = compraService.registrarCompra(compra);

        // Verificar
        assertNotNull(response);
        assertEquals("Pablo", response.getNombreUsuario());
        assertEquals("Hugo Boss", response.getNombrePerfume());
        assertEquals(2, response.getCantidad());
        assertEquals(200.0, response.getTotal());
    }

    @Test
    void testObtenerComprasPorUsuario() {
        Compra compra = new Compra();
        compra.setUsuarioId(1L);
        when(compraRepository.findByUsuarioId(1L)).thenReturn(List.of(compra));

        List<Compra> compras = compraService.obtenerComprasPorUsuario(1L);
        assertNotNull(compras);
        assertEquals(1, compras.size());
        assertEquals(1L, compras.get(0).getUsuarioId());
    }
*/}
