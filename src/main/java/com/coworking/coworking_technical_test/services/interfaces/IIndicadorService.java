package com.coworking.coworking_technical_test.services.interfaces;

import java.util.List;

import com.coworking.coworking_technical_test.shared.dto.IngresoEconomicoDTO;
import com.coworking.coworking_technical_test.shared.dto.PersonaPrimerIngresoDTO;
import com.coworking.coworking_technical_test.shared.dto.TopOperadorDTO;
import com.coworking.coworking_technical_test.shared.dto.TopPersonaDTO;
import com.coworking.coworking_technical_test.shared.dto.TopSedeFacturacionDTO;

public interface IIndicadorService {

    // ADMIN y OPERADOR
    List<TopPersonaDTO> topPersonasConMasIngresos();

    List<TopPersonaDTO> topPersonasConMasIngresosPorSede(Integer sedeId);

    List<PersonaPrimerIngresoDTO> personasPrimerIngreso();

    // OPERADOR
    IngresoEconomicoDTO ingresosEconomicos(String emailOperador);

    // ADMIN
    List<TopOperadorDTO> topOperadoresConMasIngresosSemana();

    List<TopSedeFacturacionDTO> topSedesConMayorFacturacionSemanal();

}
