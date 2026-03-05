package com.coworking.coworking_technical_test.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
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
@Tag(name = "Ingresos", description = "Registro y consulta de ingresos activos en las sedes")
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

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Consultar accesos actuales en todas las sedes", description = "Retorna la lista de todas las personas que se encuentran actualmente dentro de cualquier sede (solo ADMIN)")
    @ApiResponse(responseCode = "200", description = "Listado de accesos activos en todas las sedes")
    public ResponseEntity<List<IngresoDTO>> obtenerAccesosActuales() {
        return ResponseEntity.ok(ingresoService.obtenerAccesosActuales());
    }

    @GetMapping("/mi-sede")
    @PreAuthorize("hasRole('OPERADOR')")
    @Operation(summary = "Consultar personas en mi sede", description = "Retorna las personas que se encuentran actualmente dentro de la sede asignada al operador autenticado (solo OPERADOR)")
    @ApiResponse(responseCode = "200", description = "Listado de accesos activos en la sede del operador")
    @ApiResponse(responseCode = "404", description = "Operador sin sede asignada")
    public ResponseEntity<List<IngresoDTO>> obtenerAccesosPorSede(Authentication authentication) {
        return ResponseEntity.ok(ingresoService.obtenerAccesosPorSede(authentication.getName()));
    }

}
