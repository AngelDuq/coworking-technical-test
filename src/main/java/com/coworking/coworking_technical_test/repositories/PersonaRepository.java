package com.coworking.coworking_technical_test.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coworking.coworking_technical_test.entities.Persona;

public interface PersonaRepository extends JpaRepository<Persona, Integer> {
    
}
