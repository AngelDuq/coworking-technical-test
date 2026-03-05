package com.coworking.coworking_technical_test.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coworking.coworking_technical_test.services.interfaces.INotificacionService;
import com.coworking.coworking_technical_test.shared.request.NotificacionRequest;
import com.coworking.coworking_technical_test.shared.responses.NotificacionResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/notificaciones")
@RequiredArgsConstructor
@Tag(name = "Notificaciones", description = "Microservicio simulado de notificaciones (ADMIN/OPERADOR)")
@SecurityRequirement(name = "bearerAuth")
public class NotificacionController {

    private final INotificacionService notificacionService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'OPERADOR')")
    @Operation(summary = "Enviar notificación simulada", description = "Simula el envío de una notificación imprimiendo los datos en los logs del servidor")
    @ApiResponse(responseCode = "200", description = "Notificación enviada exitosamente (simulada)")
    public ResponseEntity<NotificacionResponse> enviarNotificacion(
            @Valid @RequestBody NotificacionRequest request) {
        NotificacionResponse response = notificacionService.enviarNotificacion(request);
        return ResponseEntity.ok(response);
    }

}
