package com.coworking.coworking_technical_test.controllers;

import com.coworking.coworking_technical_test.security.JwtTokenProvider;
import com.coworking.coworking_technical_test.services.interfaces.ITokenBlacklistService;
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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
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

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final ITokenBlacklistService tokenBlacklistService;

    @PostMapping("/login")
    @Operation(summary = "Iniciar sesión", description = "Autentica al usuario y retorna un token JWT")
    @ApiResponse(responseCode = "200", description = "Login exitoso, retorna token JWT y rol")
    @ApiResponse(responseCode = "401", description = "Credenciales inválidas")
    public ResponseEntity<TokenResponse> login(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.generateToken(authentication);

        String rol = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst()
                .orElse("ROLE_USER");

        return ResponseEntity.ok(new TokenResponse(token, rol));
    }

    @PostMapping("/logout")
    @Operation(summary = "Cerrar sesión", description = "Invalida el token JWT del usuario autenticado y lo agrega a la lista negra. El token no podrá ser reutilizado.")
    @ApiResponse(responseCode = "204", description = "Sesión cerrada exitosamente")
    @ApiResponse(responseCode = "401", description = "Token inválido o no proporcionado")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<Void> logout(
            @RequestHeader(Constants.HEADER_AUTHORIZATION_KEY) String authHeader) {
        String token = authHeader.substring(Constants.TOKEN_BEARER_PREFIX.length()).trim();
        tokenBlacklistService.blacklist(token);
        return ResponseEntity.noContent().build();
    }
}
