package com.coworking.coworking_technical_test.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Almacena el registro histórico de visitas completadas.
 * Cuando se registra una salida, el ingreso se elimina y se crea
 * un registro aquí con la información completa (ingreso, salida, valor a
 * pagar).
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "historico")
public class Historico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "persona_id", nullable = false)
    private Persona persona;

    @ManyToOne
    @JoinColumn(name = "sede_id", nullable = false)
    private Sede sede;

    @Column(name = "fecha_hora_ingreso", nullable = false)
    private LocalDateTime fechaHoraIngreso;

    @Column(name = "fecha_hora_salida", nullable = false)
    private LocalDateTime fechaHoraSalida;

    @Column(name = "valor_pagar", precision = 10, scale = 2, nullable = false)
    private BigDecimal valorPagar;

}
