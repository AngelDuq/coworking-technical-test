package com.coworking.coworking_technical_test.shared.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SedeDTO {

    private Integer id;
    private String nombre;
    private String direccion;
    private Integer capacidadMaxSimultanea;
    private BigDecimal precioHora;
    private Integer operadorId;
    private String operadorNombre;

}
