package com.coworking.coworking_technical_test.shared.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalidaRequest {

    @NotBlank(message = "El documento es obligatorio")
    @Size(max = 15, message = "El documento no puede superar 15 caracteres")
    private String documento;

}
