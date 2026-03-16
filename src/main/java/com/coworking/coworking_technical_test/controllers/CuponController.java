package com.coworking.coworking_technical_test.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coworking.coworking_technical_test.services.interfaces.ICuponService;
import com.coworking.coworking_technical_test.shared.responses.CuponRedencionResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/cupones")
@RequiredArgsConstructor
@Tag(name = "Cupones", description = "Gestión de cupones de fidelidad")
public class CuponController {
    
    private final ICuponService cuponService;

    @PostMapping("/redimir/{cuponId}")
    @Operation(summary = "Redimir cupón", description = "Redime un cupón activo y retorna la información de la operación y del cupón redimido")
    @ApiResponse(responseCode = "200", description = "Cupón redimido exitosamente")
    @ApiResponse(responseCode = "400", description = "El cupón no está activo y no puede redimirse")
    @ApiResponse(responseCode = "404", description = "Cupón no encontrado")
    public ResponseEntity<CuponRedencionResponse> redimirCupon(@PathVariable Integer cuponId) {
        return ResponseEntity.ok(cuponService.redimirCupon(cuponId));
    }

}
