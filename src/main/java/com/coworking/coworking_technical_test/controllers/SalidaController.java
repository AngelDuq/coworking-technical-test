package com.coworking.coworking_technical_test.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coworking.coworking_technical_test.services.interfaces.ISalidaService;
import com.coworking.coworking_technical_test.shared.dto.HistoricoDTO;
import com.coworking.coworking_technical_test.shared.request.SalidaRequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/salidas")
@RequiredArgsConstructor
@Tag(name = "Salidas", description = "Registro de salida de personas y generación de histórico (solo OPERADOR)")
@SecurityRequirement(name = "bearerAuth")
public class SalidaController {

    private final ISalidaService salidaService;

    @PostMapping
    @PreAuthorize("hasRole('OPERADOR')")
    @Operation(summary = "Registrar salida", description = "Registra la salida de una persona, calcula el valor a pagar, mueve el registro a histórico y verifica cupón de fidelidad")
    @ApiResponse(responseCode = "200", description = "Salida registrada, retorna el registro histórico con el valor a pagar")
    @ApiResponse(responseCode = "404", description = "Persona no encontrada o sin ingreso activo")
    public ResponseEntity<HistoricoDTO> registrarSalida(@Valid @RequestBody SalidaRequest request) {
        HistoricoDTO historico = salidaService.registrarSalida(request);
        return new ResponseEntity<>(historico, HttpStatus.OK);
    }

}
