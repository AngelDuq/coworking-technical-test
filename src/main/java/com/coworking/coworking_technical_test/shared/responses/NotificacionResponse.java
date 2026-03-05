package com.coworking.coworking_technical_test.shared.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotificacionResponse {

    private boolean exitoso;
    private String mensaje;

}
