package com.coworking.coworking_technical_test.shared.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IngresoRequest {

    @NotBlank(message = "El documento es obligatorio")
    @Size(max = 15, message = "El documento no puede superar 15 caracteres")
    private String documento;

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    private String apellido;

    @Email(message = "El email debe tener un formato válido")
    private String email;

    @NotNull(message = "El ID de la sede es obligatorio")
    private Integer sedeId;

}
