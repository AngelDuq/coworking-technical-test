package com.coworking.coworking_technical_test.shared.request;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SedeRequest {

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "La dirección es obligatoria")
    private String direccion;

    @NotNull(message = "La capacidad máxima es obligatoria")
    @Positive(message = "La capacidad máxima debe ser mayor a 0")
    private Integer capacidadMaxSimultanea;

    @NotNull(message = "El precio por hora es obligatorio")
    @Positive(message = "El precio por hora debe ser mayor a 0")
    private BigDecimal precioHora;

}
