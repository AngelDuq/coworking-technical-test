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

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/salidas")
@RequiredArgsConstructor
public class SalidaController {

    private final ISalidaService salidaService;

    @PostMapping
    @PreAuthorize("hasRole('OPERADOR')")
    public ResponseEntity<HistoricoDTO> registrarSalida(@Valid @RequestBody SalidaRequest request) {
        HistoricoDTO historico = salidaService.registrarSalida(request);
        return new ResponseEntity<>(historico, HttpStatus.OK);
    }

}
