package com.example.api_ventas_inventario.services;
    
import com.example.api_ventas_inventario.models.Perfume;
import com.example.api_ventas_inventario.repositories.PerfumeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PerfumeServiceImpl implements PerfumeService {

    @Autowired
    private PerfumeRepository perfumeRepository;

    @Override
    public List<Perfume> getAllPerfumes() {
        return perfumeRepository.findAll();
    }

    @Override
    public Optional<Perfume> getPerfumeById(Long id) {
        return perfumeRepository.findById(id);
    }

    @Override
    public Perfume savePerfume(Perfume perfume) {
        return perfumeRepository.save(perfume);
    }

    @Override
    public void deletePerfume(Long id) {
        perfumeRepository.deleteById(id);
    }
}
