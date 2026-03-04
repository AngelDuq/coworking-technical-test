package com.coworking.coworking_technical_test.mappers;

import com.coworking.coworking_technical_test.entities.Sede;
import com.coworking.coworking_technical_test.shared.dto.SedeDTO;
import com.coworking.coworking_technical_test.shared.request.SedeRequest;
import org.springframework.stereotype.Component;

@Component
public class SedeMapper {

    public SedeDTO toDTO(Sede sede) {
        SedeDTO dto = new SedeDTO();
        dto.setId(sede.getId());
        dto.setNombre(sede.getNombre());
        dto.setDireccion(sede.getDireccion());
        dto.setCapacidadMaxSimultanea(sede.getCapacidadMaxSimultanea());
        dto.setPrecioHora(sede.getPrecioHora());

        if (sede.getOperador() != null) {
            dto.setOperadorId(sede.getOperador().getId());
            dto.setOperadorNombre(sede.getOperador().getNombre() + " " + sede.getOperador().getApellido());
        }

        return dto;
    }

    public Sede toEntity(SedeRequest request) {
        Sede sede = new Sede();
        sede.setNombre(request.getNombre());
        sede.setDireccion(request.getDireccion());
        sede.setCapacidadMaxSimultanea(request.getCapacidadMaxSimultanea());
        sede.setPrecioHora(request.getPrecioHora());
        return sede;
    }

    public void updateEntity(Sede sede, SedeRequest request) {
        sede.setNombre(request.getNombre());
        sede.setDireccion(request.getDireccion());
        sede.setCapacidadMaxSimultanea(request.getCapacidadMaxSimultanea());
        sede.setPrecioHora(request.getPrecioHora());
    }

}
