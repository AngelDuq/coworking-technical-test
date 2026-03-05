package com.coworking.coworking_technical_test.shared.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotificacionRequest {

    @NotBlank(message = "El email es obligatorio")
    private String email;

    @NotBlank(message = "El documento es obligatorio")
    private String documento;

    @NotBlank(message = "El mensaje es obligatorio")
    private String mensaje;

    @NotNull(message = "El ID de la sede es obligatorio")
    private Integer sedeId;

    private String sedeNombre;

}
