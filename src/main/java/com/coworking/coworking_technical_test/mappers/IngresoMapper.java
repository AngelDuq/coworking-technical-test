package com.coworking.coworking_technical_test.mappers;

import com.coworking.coworking_technical_test.entities.Ingreso;
import com.coworking.coworking_technical_test.shared.dto.IngresoDTO;
import org.springframework.stereotype.Component;

@Component
public class IngresoMapper {

    public IngresoDTO toDTO(Ingreso ingreso) {
        IngresoDTO dto = new IngresoDTO();
        dto.setId(ingreso.getId());
        dto.setDocumento(ingreso.getPersona().getDocumento());
        dto.setNombreCompleto(ingreso.getPersona().getNombre() + " " + ingreso.getPersona().getApellido());
        dto.setSedeId(ingreso.getSede().getId());
        dto.setSedeNombre(ingreso.getSede().getNombre());
        dto.setFechaHoraIngreso(ingreso.getFechaHoraIngreso());
        return dto;
    }

}
