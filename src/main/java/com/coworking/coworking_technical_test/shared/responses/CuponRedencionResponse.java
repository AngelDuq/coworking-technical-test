package com.coworking.coworking_technical_test.shared.responses;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CuponRedencionResponse {

    private Integer cuponId;
    private String estadoAnterior;
    private String estadoActual;
    private LocalDate fechaUso;
    private LocalDate fechaVencimiento;
    private String sedeNombre;
    private String personaDocumento;
    private String personaNombre;
    private String mensaje;
    
}
