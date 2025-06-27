package com.example.api_ventas_inventario.services;


import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.api_ventas_inventario.repositories.PerfumeRepository;

public class PerfumeServiceTest {

    @Mock
    private PerfumeRepository perfumeRepository;

    @InjectMocks
    private PerfumeService perfumeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testDeletePerfume() {
        Long id = 1L;
        perfumeService.deleteById(id);
        verify(perfumeRepository, times(1)).deleteById(id);
    }
}

   /*  @Test
    void testGetAllPerfumes() {

    }

    @Test
    void testGetPerfumeById() {

    }

    @Test
    void testSavePerfume() {

    }*/

