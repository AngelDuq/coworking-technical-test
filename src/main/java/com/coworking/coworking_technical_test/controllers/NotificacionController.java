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

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/notificaciones")
@RequiredArgsConstructor
public class NotificacionController {

    private final INotificacionService notificacionService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'OPERADOR')")
    public ResponseEntity<NotificacionResponse> enviarNotificacion(
            @Valid @RequestBody NotificacionRequest request) {
        NotificacionResponse response = notificacionService.enviarNotificacion(request);
        return ResponseEntity.ok(response);
    }

}
