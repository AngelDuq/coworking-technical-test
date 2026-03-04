package com.coworking.coworking_technical_test.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coworking.coworking_technical_test.entities.Ingreso;
import com.coworking.coworking_technical_test.entities.Persona;

public interface IngresoRepository extends JpaRepository<Ingreso, Integer> {

    boolean existsByPersona(Persona persona);

    Optional<Ingreso> findByPersona(Persona persona);

    long countBySedeId(Integer sedeId);

}
