package com.example.api_perfume.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.api_perfume.models.entities.Reporte;
import com.example.api_perfume.repository.ReporteRepository;

@Service
public class Reporteservice {

    @Autowired
    private ReporteRepository reporteRepository;

    public List<Reporte> obtenerTodos() {
        return reporteRepository.findAll();
    }

    public Reporte obtenerPorId(Long id) {
        return reporteRepository.findById(id).orElse(null);
    }

    public Reporte crear(Reporte reporte) {
        return reporteRepository.save(reporte);
    }

    public Reporte actualizar(Long id, Reporte datos) {
        Reporte r = reporteRepository.findById(id).orElse(null);
        if (r != null) {
            r.setTipo(datos.getTipo());
            r.setSucursal(datos.getSucursal());
            r.setFechaGeneracion(datos.getFechaGeneracion());
            r.setContenido(datos.getContenido());
            return reporteRepository.save(r);
        }
        return null;
    }

    public void eliminar(Long id) {
        reporteRepository.deleteById(id);
    }

    public List<Reporte> buscarPorTipo(String tipo) {
        return reporteRepository.findByTipo(tipo);
    }

    public List<Reporte> buscarPorSucursal(String sucursal) {
        return reporteRepository.findBySucursal(sucursal);
    }

    public List<Reporte> buscarPorRangoDeFecha(LocalDate desde, LocalDate hasta) {
        return reporteRepository.findByFechaGeneracionBetween(desde, hasta);
    }
    
}
