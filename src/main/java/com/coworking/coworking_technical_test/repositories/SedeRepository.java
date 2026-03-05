package com.coworking.coworking_technical_test.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coworking.coworking_technical_test.entities.Sede;

public interface SedeRepository extends JpaRepository<Sede, Integer> {

    Optional<Sede> findByOperadorId(Integer operadorId);

}
