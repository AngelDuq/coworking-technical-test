package com.coworking.coworking_technical_test.shared.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenResponse {

    private String token;
    private String tipo = "Bearer";
    private String rol;
    private String mensaje = "Inicio de sesión exitoso";

    public TokenResponse(String token, String rol) {
        this.token = token;
        this.rol = rol;
    }

}
