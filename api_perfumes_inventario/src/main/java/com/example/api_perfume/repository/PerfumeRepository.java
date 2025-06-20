package com.example.api_perfume.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.api_perfume.models.Perfume;

public interface PerfumeRepository extends JpaRepository<Perfume, Long> {

}
