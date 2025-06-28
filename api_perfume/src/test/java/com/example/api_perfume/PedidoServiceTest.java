package com.example.api_perfume;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
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

import com.example.api_perfume.models.entities.Pedido;
import com.example.api_perfume.repository.PedidoRepository;
import com.example.api_perfume.services.PedidoService;

@ExtendWith(MockitoExtension.class)
class PedidoServiceTest {

    @Mock
    private PedidoRepository pedidoRepository;

    @InjectMocks
    private PedidoService pedidoService;

    private Pedido crearPedido(Long id, String sucursal, String estado) {
        Pedido pedido = new Pedido();
        pedido.setId(id);
        pedido.setSucursal(sucursal);
        pedido.setEstado(estado);
        pedido.setFechaPedido(LocalDate.now());
        pedido.setObservaciones("Observación de prueba");
        pedido.setProductosSolicitados("ProductoA:10, ProductoB:5");
        return pedido;
    }

    @Test
    void testListarTodos() {
        Pedido p1 = crearPedido(1L, "Sucursal A", "pendiente");
        Pedido p2 = crearPedido(2L, "Sucursal B", "enviado");

        when(pedidoRepository.findAll()).thenReturn(Arrays.asList(p1, p2));

        List<Pedido> pedidos = pedidoService.listarTodos();

        assertEquals(2, pedidos.size());
        verify(pedidoRepository).findAll();
    }

    @Test
    void testObtenerPorId_existente() {
        Pedido pedido = crearPedido(1L, "Sucursal X", "pendiente");

        when(pedidoRepository.findById(1L)).thenReturn(Optional.of(pedido));

        Pedido resultado = pedidoService.obtenerPorId(1L);

        assertNotNull(resultado);
        assertEquals("Sucursal X", resultado.getSucursal());
    }

    @Test
    void testObtenerPorId_noExistente() {
        when(pedidoRepository.findById(99L)).thenReturn(Optional.empty());

        Pedido resultado = pedidoService.obtenerPorId(99L);

        assertNull(resultado);
    }

    @Test
    void testCrear() {
        Pedido nuevo = new Pedido();
        nuevo.setSucursal("Sucursal Z");
        nuevo.setProductosSolicitados("ProductoX:3");

        Pedido esperado = crearPedido(10L, "Sucursal Z", "pendiente");

        when(pedidoRepository.save(any(Pedido.class))).thenReturn(esperado);

        Pedido resultado = pedidoService.crear(nuevo);

        assertEquals("pendiente", resultado.getEstado());
        assertNotNull(resultado.getFechaPedido());
        verify(pedidoRepository).save(nuevo);
    }

    @Test
    void testActualizar_existente() {
        Pedido existente = crearPedido(1L, "Sucursal A", "pendiente");
        Pedido actualizacion = new Pedido();
        actualizacion.setSucursal("Sucursal B");
        actualizacion.setProductosSolicitados("ProductoC:4");
        actualizacion.setEstado("enviado");
        actualizacion.setObservaciones("Actualizado");

        when(pedidoRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(pedidoRepository.save(any(Pedido.class))).thenAnswer(inv -> inv.getArgument(0));

        Pedido resultado = pedidoService.actualizar(1L, actualizacion);

        assertEquals("Sucursal B", resultado.getSucursal());
        assertEquals("enviado", resultado.getEstado());
        assertEquals("ProductoC:4", resultado.getProductosSolicitados());
        assertEquals("Actualizado", resultado.getObservaciones());
    }

    @Test
    void testActualizar_noExistente() {
        Pedido actualizacion = crearPedido(null, "Sucursal Y", "pendiente");

        when(pedidoRepository.findById(99L)).thenReturn(Optional.empty());

        Pedido resultado = pedidoService.actualizar(99L, actualizacion);

        assertNull(resultado);
    }

    @Test
    void testEliminar() {
        doNothing().when(pedidoRepository).deleteById(1L);

        pedidoService.eliminar(1L);

        verify(pedidoRepository).deleteById(1L);
    }

    @Test
    void testBuscarPorSucursal() {
        Pedido p1 = crearPedido(1L, "Sucursal Norte", "pendiente");
        Pedido p2 = crearPedido(2L, "Sucursal Norte", "entregado");

        when(pedidoRepository.findBySucursal("Sucursal Norte")).thenReturn(List.of(p1, p2));

        List<Pedido> resultado = pedidoService.buscarPorSucursal("Sucursal Norte");

        assertEquals(2, resultado.size());
    }

    @Test
    void testBuscarPorEstado() {
        Pedido p1 = crearPedido(1L, "Sucursal Sur", "pendiente");

        when(pedidoRepository.findByEstado("pendiente")).thenReturn(List.of(p1));

        List<Pedido> resultado = pedidoService.buscarPorEstado("pendiente");

        assertEquals(1, resultado.size());
        assertEquals("pendiente", resultado.get(0).getEstado());
    }
}
