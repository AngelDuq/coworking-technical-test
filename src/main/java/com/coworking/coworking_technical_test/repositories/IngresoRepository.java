package com.coworking.coworking_technical_test.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.coworking.coworking_technical_test.entities.Ingreso;
import com.coworking.coworking_technical_test.entities.Persona;

public interface IngresoRepository extends JpaRepository<Ingreso, Integer> {

    boolean existsByPersona(Persona persona);

    Optional<Ingreso> findByPersona(Persona persona);

    long countBySedeId(Integer sedeId);

    // Personas con ingreso activo que no tienen registros en histórico (primera
    // vez)
    @Query("SELECT i FROM Ingreso i WHERE i.persona.id NOT IN (SELECT DISTINCT h.persona.id FROM Historico h)")
    List<Ingreso> findPersonasPrimerIngreso();

}
