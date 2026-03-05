package com.coworking.coworking_technical_test.shared.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonaPrimerIngresoDTO {

    private String documento;
    private String nombreCompleto;
    private String sedeNombre;
    private LocalDateTime fechaHoraIngreso;

}
