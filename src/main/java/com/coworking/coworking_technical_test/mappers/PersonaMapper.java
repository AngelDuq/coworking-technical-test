package com.coworking.coworking_technical_test.mappers;

import org.springframework.stereotype.Component;

import com.coworking.coworking_technical_test.entities.Persona;
import com.coworking.coworking_technical_test.shared.dto.PersonaDTO;
import com.coworking.coworking_technical_test.shared.request.PersonaRequest;

@Component
public class PersonaMapper {

    public PersonaDTO toDTO(Persona persona) {
        PersonaDTO dto = new PersonaDTO();
        dto.setId(persona.getId());
        dto.setDocumento(persona.getDocumento());
        dto.setNombre(persona.getNombre());
        dto.setApellido(persona.getApellido());
        dto.setEmail(persona.getEmail());
        return dto;
    }

    public Persona toEntity(PersonaRequest request) {
        Persona persona = new Persona();
        persona.setDocumento(request.getDocumento());
        persona.setNombre(request.getNombre());
        persona.setApellido(request.getApellido());
        persona.setEmail(request.getEmail());
        return persona;
    }

    public void updateEntity(Persona persona, PersonaRequest request) {
        persona.setDocumento(request.getDocumento());
        persona.setNombre(request.getNombre());
        persona.setApellido(request.getApellido());
        persona.setEmail(request.getEmail());
    }

}
