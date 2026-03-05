package com.coworking.coworking_technical_test.shared.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TopOperadorDTO {

    private Integer operadorId;
    private String nombreCompleto;
    private Long totalIngresos;

}
