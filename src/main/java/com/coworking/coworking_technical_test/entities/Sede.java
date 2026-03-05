package com.coworking.coworking_technical_test.entities;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "sede")
public class Sede {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    @Column(length = 100, nullable = false)
    private String nombre;

    @NotBlank
    @Column(length = 200, nullable = false)
    private String direccion;

    @NotNull
    @Positive
    @Column(name = "capacidad_max_simultanea", nullable = false)
    private Integer capacidadMaxSimultanea;

    @NotNull
    @Positive
    @Column(name = "precio_hora", precision = 10, scale = 2, nullable = false)
    private BigDecimal precioHora;

    @ManyToOne
    @JoinColumn(name = "operador_id")
    private Usuario operador;
}
