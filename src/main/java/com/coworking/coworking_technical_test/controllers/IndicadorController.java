package com.coworking.coworking_technical_test.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coworking.coworking_technical_test.services.interfaces.IIndicadorService;
import com.coworking.coworking_technical_test.shared.dto.IngresoEconomicoDTO;
import com.coworking.coworking_technical_test.shared.dto.PersonaPrimerIngresoDTO;
import com.coworking.coworking_technical_test.shared.dto.TopOperadorDTO;
import com.coworking.coworking_technical_test.shared.dto.TopPersonaDTO;
import com.coworking.coworking_technical_test.shared.dto.TopSedeFacturacionDTO;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/indicadores")
@RequiredArgsConstructor
public class IndicadorController {

    private final IIndicadorService indicadorService;

    @GetMapping("/top-personas")
    @PreAuthorize("hasAnyRole('ADMIN', 'OPERADOR')")
    public ResponseEntity<List<TopPersonaDTO>> topPersonasConMasIngresos() {
        return ResponseEntity.ok(indicadorService.topPersonasConMasIngresos());
    }

    @GetMapping("/top-personas/sede/{sedeId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'OPERADOR')")
    public ResponseEntity<List<TopPersonaDTO>> topPersonasConMasIngresosPorSede(@PathVariable Integer sedeId) {
        return ResponseEntity.ok(indicadorService.topPersonasConMasIngresosPorSede(sedeId));
    }

    @GetMapping("/primer-ingreso")
    @PreAuthorize("hasAnyRole('ADMIN', 'OPERADOR')")
    public ResponseEntity<List<PersonaPrimerIngresoDTO>> personasPrimerIngreso() {
        return ResponseEntity.ok(indicadorService.personasPrimerIngreso());
    }

    @GetMapping("/ingresos-economicos")
    @PreAuthorize("hasRole('OPERADOR')")
    public ResponseEntity<IngresoEconomicoDTO> ingresosEconomicos(Authentication authentication) {
        return ResponseEntity.ok(indicadorService.ingresosEconomicos(authentication.getName()));
    }

    @GetMapping("/top-operadores")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<TopOperadorDTO>> topOperadoresConMasIngresosSemana() {
        return ResponseEntity.ok(indicadorService.topOperadoresConMasIngresosSemana());
    }

    @GetMapping("/top-sedes-facturacion")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<TopSedeFacturacionDTO>> topSedesConMayorFacturacionSemanal() {
        return ResponseEntity.ok(indicadorService.topSedesConMayorFacturacionSemanal());
    }

}
