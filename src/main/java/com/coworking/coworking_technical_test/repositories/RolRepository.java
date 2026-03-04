package com.coworking.coworking_technical_test.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coworking.coworking_technical_test.entities.Rol;

public interface RolRepository extends JpaRepository<Rol, Integer> {

    Optional<Rol> findByDescripcion(String descripcion);

}
