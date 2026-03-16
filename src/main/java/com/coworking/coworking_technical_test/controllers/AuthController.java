package com.coworking.coworking_technical_test.controllers;

import com.coworking.coworking_technical_test.services.interfaces.IAuthService;
import com.coworking.coworking_technical_test.shared.request.LoginRequest;
import com.coworking.coworking_technical_test.shared.responses.TokenResponse;
import com.coworking.coworking_technical_test.shared.util.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Autenticación", description = "Endpoint de inicio de sesión y obtención de token JWT")
public class AuthController {

    private final IAuthService authService;

    @PostMapping("/login")
    @Operation(summary = "Iniciar sesión", description = "Autentica al usuario y retorna un token JWT")
    @ApiResponse(responseCode = "200", description = "Login exitoso, retorna token JWT y rol")
    @ApiResponse(responseCode = "401", description = "Credenciales inválidas")
    public ResponseEntity<TokenResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.login(loginRequest));
    }

    @PostMapping("/logout")
    @Operation(summary = "Cerrar sesión", description = "Invalida el token JWT del usuario autenticado y lo agrega a la lista negra. El token no podrá ser reutilizado.")
    @ApiResponse(responseCode = "204", description = "Sesión cerrada exitosamente")
    @ApiResponse(responseCode = "401", description = "Token inválido o no proporcionado")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<Void> logout(
            @RequestHeader(Constants.HEADER_AUTHORIZATION_KEY) String authHeader) {
        authService.logout(authHeader);
        return ResponseEntity.noContent().build();
    }
}
