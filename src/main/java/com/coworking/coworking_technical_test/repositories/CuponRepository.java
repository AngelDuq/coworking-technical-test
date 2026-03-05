package com.coworking.coworking_technical_test.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coworking.coworking_technical_test.entities.Cupon;
import com.coworking.coworking_technical_test.entities.Cupon.EstadoCupon;

public interface CuponRepository extends JpaRepository<Cupon, Integer> {

    boolean existsByPersonaIdAndSedeId(Integer personaId, Integer sedeId);

    List<Cupon> findByEstadoAndFechaVencimientoBefore(EstadoCupon estado, LocalDate fecha);

}
