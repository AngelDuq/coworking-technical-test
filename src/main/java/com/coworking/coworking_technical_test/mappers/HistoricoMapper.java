package com.coworking.coworking_technical_test.mappers;

import com.coworking.coworking_technical_test.entities.Historico;
import com.coworking.coworking_technical_test.shared.dto.HistoricoDTO;
import org.springframework.stereotype.Component;

@Component
public class HistoricoMapper {

    public HistoricoDTO toDTO(Historico historico) {
        HistoricoDTO dto = new HistoricoDTO();
        dto.setId(historico.getId());
        dto.setDocumento(historico.getPersona().getDocumento());
        dto.setNombreCompleto(historico.getPersona().getNombre() + " " + historico.getPersona().getApellido());
        dto.setSedeId(historico.getSede().getId());
        dto.setSedeNombre(historico.getSede().getNombre());
        dto.setFechaHoraIngreso(historico.getFechaHoraIngreso());
        dto.setFechaHoraSalida(historico.getFechaHoraSalida());
        dto.setValorPagar(historico.getValorPagar());
        return dto;
    }

}
