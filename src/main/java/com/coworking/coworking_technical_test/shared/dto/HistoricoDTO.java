package com.coworking.coworking_technical_test.shared.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HistoricoDTO {

    private Integer id;
    private String documento;
    private String nombreCompleto;
    private Integer sedeId;
    private String sedeNombre;
    private LocalDateTime fechaHoraIngreso;
    private LocalDateTime fechaHoraSalida;
    private BigDecimal valorPagar;

}
