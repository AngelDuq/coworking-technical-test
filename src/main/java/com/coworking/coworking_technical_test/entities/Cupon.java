package com.coworking.coworking_technical_test.entities;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Cupón de fidelidad otorgado cuando una persona acumula más de 20 horas
 * en una misma sede. Se otorga una única vez por persona y por sede.
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cupon", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "persona_id", "sede_id" })
})
public class Cupon {

    public enum EstadoCupon {
        ACTIVO,
        UTILIZADO,
        EXPIRADO
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "persona_id", nullable = false)
    private Persona persona;

    @ManyToOne
    @JoinColumn(name = "sede_id", nullable = false)
    private Sede sede;

    @Column(name = "fecha_generacion", nullable = false)
    private LocalDate fechaGeneracion;

    @Column(name = "fecha_vencimiento", nullable = false)
    private LocalDate fechaVencimiento;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EstadoCupon estado = EstadoCupon.ACTIVO;

    @Column(name = "fecha_uso")
    private LocalDate fechaUso;

}
