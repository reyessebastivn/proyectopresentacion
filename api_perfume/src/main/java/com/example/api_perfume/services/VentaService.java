package com.example.api_perfume.services;

import com.example.api_perfume.config.UsuarioServiceClient;
import com.example.api_perfume.models.entities.Perfume;
import com.example.api_perfume.models.entities.Venta;
import com.example.api_perfume.repository.PerfumeRepository;
import com.example.api_perfume.repository.VentaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VentaService {

    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    private PerfumeRepository perfumeRepository;

    @Autowired
    private UsuarioServiceClient usuarioClient;

    
    public Venta realizarVenta(Long idUsuario, String sucursal, Long idPerfume, int cantidad) throws Exception {

        // Paso 1: Obtener datos del usuario desde Microservicio01
        UsuarioServiceClient.UsuarioResponse usuario = usuarioClient.obtenerUsuarioPorId(idUsuario);

        // Paso 2: Obtener el perfume desde la base de datos local (api_perfume)
        Optional<Perfume> optionalPerfume = perfumeRepository.findById(idPerfume);

        if (optionalPerfume.isEmpty()) {
            throw new Exception("Perfume no encontrado con id: " + idPerfume);
        }

        Perfume perfume = optionalPerfume.get();

        // Paso 3: Validar si hay stock suficiente
        if (perfume.getStock() < cantidad) {
            throw new Exception("Stock insuficiente para el perfume: " + perfume.getMarca());
        }

        // Paso 4: Calcular el total de la venta
        double total = cantidad * perfume.getPrecio();

        // Paso 5: Descontar el stock
        perfume.setStock(perfume.getStock() - cantidad);
        perfumeRepository.save(perfume);  // Actualizar el stock en la base de datos

        // Paso 6: Crear y guardar la venta (boleta)
        Venta venta = Venta.builder()
                .nombreUsuario(usuario.getNombre())
                .rutUsuario(usuario.getRut())
                .sucursal(sucursal)
                .nombrePerfume(perfume.getMarca())
                .cantidad(cantidad)
                .total(total)
                .build();

        return ventaRepository.save(venta); 
    }
}
