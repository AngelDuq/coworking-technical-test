package com.coworking.coworking_technical_test.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coworking.coworking_technical_test.services.interfaces.IIngresoService;
import com.coworking.coworking_technical_test.shared.dto.IngresoDTO;
import com.coworking.coworking_technical_test.shared.request.IngresoRequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/ingresos")
@RequiredArgsConstructor
@Tag(name = "Ingresos", description = "Registro de ingreso de personas a las sedes (solo OPERADOR)")
@SecurityRequirement(name = "bearerAuth")
public class IngresoController {

    private final IIngresoService ingresoService;

    @PostMapping
    @PreAuthorize("hasRole('OPERADOR')")
    @Operation(summary = "Registrar ingreso", description = "Registra el ingreso de una persona a una sede. Si la persona no existe, la crea con documento, nombre, apellido y email proporcionados.")
    @ApiResponse(responseCode = "201", description = "Ingreso registrado exitosamente")
    @ApiResponse(responseCode = "400", description = "Persona con ingreso activo, sede sin capacidad o email con formato inválido")
    @ApiResponse(responseCode = "404", description = "Sede no encontrada")
    public ResponseEntity<IngresoDTO> registrarIngreso(@Valid @RequestBody IngresoRequest request) {
        IngresoDTO ingreso = ingresoService.registrarIngreso(request);
        return new ResponseEntity<>(ingreso, HttpStatus.CREATED);
    }

}
