package com.example.api_perfume.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.api_perfume.models.entities.Pedido;
import com.example.api_perfume.repository.PedidoRepository;

@Service
public class PedidoService {

     @Autowired
    private PedidoRepository pedidoRepository;

    public List<Pedido> listarTodos() {
        return pedidoRepository.findAll();
    }

    public Pedido obtenerPorId(Long id) {
        return pedidoRepository.findById(id).orElse(null);
    }

    public Pedido crear(Pedido pedido) {
        pedido.setEstado("pendiente");
        pedido.setFechaPedido(LocalDate.now());
        return pedidoRepository.save(pedido);
    }

    public Pedido actualizar(Long id, Pedido datos) {
        Pedido p = pedidoRepository.findById(id).orElse(null);
        if (p != null) {
            p.setSucursal(datos.getSucursal());
            p.setProductosSolicitados(datos.getProductosSolicitados());
            p.setEstado(datos.getEstado());
            p.setObservaciones(datos.getObservaciones());
            return pedidoRepository.save(p);
        }
        return null;
    }

    public void eliminar(Long id) {
        pedidoRepository.deleteById(id);
    }

    public List<Pedido> buscarPorSucursal(String sucursal) {
        return pedidoRepository.findBySucursal(sucursal);
    }

    public List<Pedido> buscarPorEstado(String estado) {
        return pedidoRepository.findByEstado(estado);
    }
    
}
