package com.coworking.coworking_technical_test.shared.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TopSedeFacturacionDTO {

    private Integer sedeId;
    private String sedeNombre;
    private BigDecimal facturacionSemanal;

}
