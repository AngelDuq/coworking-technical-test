package com.coworking.coworking_technical_test.shared.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AsignarOperadorRequest {

    @NotNull(message = "El ID del operador es obligatorio")
    private Integer operadorId;

}
