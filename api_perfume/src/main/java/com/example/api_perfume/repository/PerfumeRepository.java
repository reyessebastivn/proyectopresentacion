package com.example.api_perfume.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.api_perfume.models.Perfume;
@Repository
public interface PerfumeRepository extends JpaRepository<Perfume, Long> {

}
