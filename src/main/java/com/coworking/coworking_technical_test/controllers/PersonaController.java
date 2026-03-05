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

import com.coworking.coworking_technical_test.services.interfaces.IPersonaService;
import com.coworking.coworking_technical_test.shared.dto.PersonaDTO;
import com.coworking.coworking_technical_test.shared.request.PersonaRequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/personas")
@PreAuthorize("hasAnyRole('ADMIN', 'OPERADOR')")
@RequiredArgsConstructor
@Tag(name = "Personas", description = "Gestión de personas/clientes del coworking (ADMIN/OPERADOR)")
@SecurityRequirement(name = "bearerAuth")
public class PersonaController {

    private final IPersonaService personaService;

    @PostMapping
    @Operation(summary = "Crear persona", description = "Registra una nueva persona/cliente en el sistema")
    @ApiResponse(responseCode = "201", description = "Persona creada exitosamente")
    @ApiResponse(responseCode = "400", description = "Documento ya registrado")
    public ResponseEntity<PersonaDTO> crear(@Valid @RequestBody PersonaRequest request) {
        PersonaDTO persona = personaService.crear(request);
        return new ResponseEntity<>(persona, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener persona por ID", description = "Retorna la información de una persona específica")
    @ApiResponse(responseCode = "200", description = "Persona encontrada")
    @ApiResponse(responseCode = "404", description = "Persona no encontrada")
    public ResponseEntity<PersonaDTO> obtenerPorId(@PathVariable Integer id) {
        PersonaDTO persona = personaService.obtenerPorId(id);
        return ResponseEntity.ok(persona);
    }

    @GetMapping
    @Operation(summary = "Obtener todas las personas", description = "Retorna el listado de todas las personas registradas")
    @ApiResponse(responseCode = "200", description = "Listado de personas")
    public ResponseEntity<List<PersonaDTO>> obtenerTodas() {
        List<PersonaDTO> personas = personaService.obtenerTodas();
        return ResponseEntity.ok(personas);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar persona", description = "Actualiza los datos de una persona existente")
    @ApiResponse(responseCode = "200", description = "Persona actualizada exitosamente")
    @ApiResponse(responseCode = "404", description = "Persona no encontrada")
    @ApiResponse(responseCode = "400", description = "Documento ya registrado por otra persona")
    public ResponseEntity<PersonaDTO> actualizar(@PathVariable Integer id,
            @Valid @RequestBody PersonaRequest request) {
        PersonaDTO persona = personaService.actualizar(id, request);
        return ResponseEntity.ok(persona);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar persona", description = "Elimina una persona del sistema")
    @ApiResponse(responseCode = "204", description = "Persona eliminada exitosamente")
    @ApiResponse(responseCode = "404", description = "Persona no encontrada")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        personaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

}
