package com.coworking.coworking_technical_test.shared.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IngresoEconomicoDTO {

    private BigDecimal ingresoHoy;
    private BigDecimal ingresoSemana;
    private BigDecimal ingresoMes;
    private BigDecimal ingresoAnio;

}
