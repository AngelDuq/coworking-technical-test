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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/indicadores")
@RequiredArgsConstructor
@Tag(name = "Indicadores", description = "Métricas e indicadores del coworking")
@SecurityRequirement(name = "bearerAuth")
public class IndicadorController {

    private final IIndicadorService indicadorService;

    @GetMapping("/top-personas")
    @PreAuthorize("hasAnyRole('ADMIN', 'OPERADOR')")
    @Operation(summary = "Top 10 personas con más ingresos", description = "Retorna las 10 personas con mayor cantidad de visitas registradas en el histórico (ADMIN/OPERADOR)")
    @ApiResponse(responseCode = "200", description = "Listado top 10 personas")
    public ResponseEntity<List<TopPersonaDTO>> topPersonasConMasIngresos() {
        return ResponseEntity.ok(indicadorService.topPersonasConMasIngresos());
    }

    @GetMapping("/top-personas/sede/{sedeId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'OPERADOR')")
    @Operation(summary = "Top 10 personas con más ingresos por sede", description = "Retorna las 10 personas con mayor cantidad de visitas en una sede específica (ADMIN/OPERADOR)")
    @ApiResponse(responseCode = "200", description = "Listado top 10 personas por sede")
    @ApiResponse(responseCode = "404", description = "Sede no encontrada")
    public ResponseEntity<List<TopPersonaDTO>> topPersonasConMasIngresosPorSede(@PathVariable Integer sedeId) {
        return ResponseEntity.ok(indicadorService.topPersonasConMasIngresosPorSede(sedeId));
    }

    @GetMapping("/primer-ingreso")
    @PreAuthorize("hasAnyRole('ADMIN', 'OPERADOR')")
    @Operation(summary = "Personas que ingresan por primera vez", description = "Retorna las personas con ingreso activo que no tienen registros previos en el histórico (ADMIN/OPERADOR)")
    @ApiResponse(responseCode = "200", description = "Listado de personas con primer ingreso")
    public ResponseEntity<List<PersonaPrimerIngresoDTO>> personasPrimerIngreso() {
        return ResponseEntity.ok(indicadorService.personasPrimerIngreso());
    }

    @GetMapping("/ingresos-economicos")
    @PreAuthorize("hasRole('OPERADOR')")
    @Operation(summary = "Ingresos económicos", description = "Retorna la facturación de la sede del operador autenticado: hoy, semana, mes y año (solo OPERADOR)")
    @ApiResponse(responseCode = "200", description = "Resumen de ingresos económicos")
    @ApiResponse(responseCode = "404", description = "Operador o sede no encontrados")
    public ResponseEntity<IngresoEconomicoDTO> ingresosEconomicos(Authentication authentication) {
        return ResponseEntity.ok(indicadorService.ingresosEconomicos(authentication.getName()));
    }

    @GetMapping("/top-operadores")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Top 3 operadores con más ingresos en la semana", description = "Retorna los 3 operadores cuyas sedes registraron más visitas en la semana actual (solo ADMIN)")
    @ApiResponse(responseCode = "200", description = "Top 3 operadores")
    public ResponseEntity<List<TopOperadorDTO>> topOperadoresConMasIngresosSemana() {
        return ResponseEntity.ok(indicadorService.topOperadoresConMasIngresosSemana());
    }

    @GetMapping("/top-sedes-facturacion")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Top 3 sedes con mayor facturación semanal", description = "Retorna las 3 sedes con mayor facturación en la semana actual (solo ADMIN)")
    @ApiResponse(responseCode = "200", description = "Top 3 sedes por facturación")
    public ResponseEntity<List<TopSedeFacturacionDTO>> topSedesConMayorFacturacionSemanal() {
        return ResponseEntity.ok(indicadorService.topSedesConMayorFacturacionSemanal());
    }

}
