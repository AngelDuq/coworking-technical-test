package com.coworking.coworking_technical_test.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coworking.coworking_technical_test.entities.Ingreso;

public interface IngresoRepository extends JpaRepository<Ingreso, Integer> {
    
}
