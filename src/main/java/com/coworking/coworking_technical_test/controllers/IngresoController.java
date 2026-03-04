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

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/ingresos")
@RequiredArgsConstructor
public class IngresoController {

    private final IIngresoService ingresoService;

    @PostMapping
    @PreAuthorize("hasRole('OPERADOR')")
    public ResponseEntity<IngresoDTO> registrarIngreso(@Valid @RequestBody IngresoRequest request) {
        IngresoDTO ingreso = ingresoService.registrarIngreso(request);
        return new ResponseEntity<>(ingreso, HttpStatus.CREATED);
    }

}
