package com.coworking.coworking_technical_test.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coworking.coworking_technical_test.services.interfaces.ISedeService;
import com.coworking.coworking_technical_test.shared.dto.SedeDTO;
import com.coworking.coworking_technical_test.shared.request.AsignarOperadorRequest;
import com.coworking.coworking_technical_test.shared.request.SedeRequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/sedes")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
@Tag(name = "Sedes", description = "CRUD de sedes del coworking (solo ADMIN)")
@SecurityRequirement(name = "bearerAuth")
public class SedeController {

    private final ISedeService sedeService;

    @PostMapping
    @Operation(summary = "Crear sede", description = "Crea una nueva sede del coworking")
    @ApiResponse(responseCode = "201", description = "Sede creada exitosamente")
    public ResponseEntity<SedeDTO> crear(@Valid @RequestBody SedeRequest request) {
        SedeDTO sede = sedeService.crear(request);
        return new ResponseEntity<>(sede, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener sede por ID", description = "Retorna la información de una sede específica")
    @ApiResponse(responseCode = "200", description = "Sede encontrada")
    @ApiResponse(responseCode = "404", description = "Sede no encontrada")
    public ResponseEntity<SedeDTO> obtenerPorId(@PathVariable Integer id) {
        SedeDTO sede = sedeService.obtenerPorId(id);
        return ResponseEntity.ok(sede);
    }

    @GetMapping
    @Operation(summary = "Obtener todas las sedes", description = "Retorna el listado de todas las sedes")
    @ApiResponse(responseCode = "200", description = "Listado de sedes")
    public ResponseEntity<List<SedeDTO>> obtenerTodas() {
        List<SedeDTO> sedes = sedeService.obtenerTodas();
        return ResponseEntity.ok(sedes);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar sede", description = "Actualiza la información de una sede existente")
    @ApiResponse(responseCode = "200", description = "Sede actualizada exitosamente")
    @ApiResponse(responseCode = "404", description = "Sede no encontrada")
    public ResponseEntity<SedeDTO> actualizar(@PathVariable Integer id,
            @Valid @RequestBody SedeRequest request) {
        SedeDTO sede = sedeService.actualizar(id, request);
        return ResponseEntity.ok(sede);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar sede", description = "Elimina una sede del sistema")
    @ApiResponse(responseCode = "204", description = "Sede eliminada exitosamente")
    @ApiResponse(responseCode = "404", description = "Sede no encontrada")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        sedeService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/operador")
    @Operation(summary = "Asignar operador a sede", description = "Asigna un usuario con rol OPERADOR a una sede")
    @ApiResponse(responseCode = "200", description = "Operador asignado exitosamente")
    @ApiResponse(responseCode = "404", description = "Sede o usuario no encontrado")
    @ApiResponse(responseCode = "400", description = "El usuario no tiene rol OPERADOR")
    public ResponseEntity<SedeDTO> asignarOperador(@PathVariable Integer id,
            @Valid @RequestBody AsignarOperadorRequest request) {
        SedeDTO sede = sedeService.asignarOperador(id, request.getOperadorId());
        return ResponseEntity.ok(sede);
    }

}
