package com.coworking.coworking_technical_test.repositories;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.coworking.coworking_technical_test.entities.Historico;

public interface HistoricoRepository extends JpaRepository<Historico, Integer> {

    // Top personas con más ingresos (global)
    @Query("SELECT h.persona.documento, CONCAT(h.persona.nombre, ' ', h.persona.apellido), COUNT(h) "
            + "FROM Historico h GROUP BY h.persona.id, h.persona.documento, h.persona.nombre, h.persona.apellido "
            + "ORDER BY COUNT(h) DESC")
    List<Object[]> findTopPersonasConMasIngresos(Pageable pageable);

    // Top personas con más ingresos por sede
    @Query("SELECT h.persona.documento, CONCAT(h.persona.nombre, ' ', h.persona.apellido), COUNT(h) "
            + "FROM Historico h WHERE h.sede.id = :sedeId "
            + "GROUP BY h.persona.id, h.persona.documento, h.persona.nombre, h.persona.apellido "
            + "ORDER BY COUNT(h) DESC")
    List<Object[]> findTopPersonasConMasIngresosPorSede(@Param("sedeId") Integer sedeId, Pageable pageable);

    // Sumar ingresos económicos por sede y rango de fechas
    @Query("SELECT COALESCE(SUM(h.valorPagar), 0) FROM Historico h "
            + "WHERE h.sede.id = :sedeId AND h.fechaHoraSalida BETWEEN :desde AND :hasta")
    BigDecimal sumarIngresosPorSedeYRango(@Param("sedeId") Integer sedeId,
            @Param("desde") LocalDateTime desde, @Param("hasta") LocalDateTime hasta);

    // Top operadores con más ingresos en rango de fechas
    @Query("SELECT h.sede.operador.id, CONCAT(h.sede.operador.nombre, ' ', h.sede.operador.apellido), COUNT(h) "
            + "FROM Historico h WHERE h.fechaHoraSalida BETWEEN :desde AND :hasta "
            + "AND h.sede.operador IS NOT NULL "
            + "GROUP BY h.sede.operador.id, h.sede.operador.nombre, h.sede.operador.apellido "
            + "ORDER BY COUNT(h) DESC")
    List<Object[]> findTopOperadoresConMasIngresos(@Param("desde") LocalDateTime desde,
            @Param("hasta") LocalDateTime hasta, Pageable pageable);

    // Top sedes con mayor facturación en rango de fechas
    @Query("SELECT h.sede.id, h.sede.nombre, COALESCE(SUM(h.valorPagar), 0) "
            + "FROM Historico h WHERE h.fechaHoraSalida BETWEEN :desde AND :hasta "
            + "GROUP BY h.sede.id, h.sede.nombre "
            + "ORDER BY COALESCE(SUM(h.valorPagar), 0) DESC")
    List<Object[]> findTopSedesConMayorFacturacion(@Param("desde") LocalDateTime desde,
            @Param("hasta") LocalDateTime hasta, Pageable pageable);

    // Verificar si persona tiene registros históricos
    boolean existsByPersonaId(Integer personaId);

    // Sumar minutos de permanencia de una persona en una sede
    @Query("SELECT COALESCE(SUM(TIMESTAMPDIFF(MINUTE, h.fechaHoraIngreso, h.fechaHoraSalida)), 0) "
            + "FROM Historico h WHERE h.persona.id = :personaId AND h.sede.id = :sedeId")
    Long sumarMinutosPermanenciaPorPersonaYSede(@Param("personaId") Integer personaId,
            @Param("sedeId") Integer sedeId);

}
