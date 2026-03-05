package com.coworking.coworking_technical_test.services.implementations;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.coworking.coworking_technical_test.entities.Persona;
import com.coworking.coworking_technical_test.exceptions.BusinessException;
import com.coworking.coworking_technical_test.exceptions.NotFoundException;
import com.coworking.coworking_technical_test.mappers.PersonaMapper;
import com.coworking.coworking_technical_test.repositories.PersonaRepository;
import com.coworking.coworking_technical_test.services.interfaces.IPersonaService;
import com.coworking.coworking_technical_test.shared.dto.PersonaDTO;
import com.coworking.coworking_technical_test.shared.request.PersonaRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PersonaServiceImpl implements IPersonaService {

    private final PersonaRepository personaRepository;
    private final PersonaMapper personaMapper;
    private final MessageSource messageSource;

    @Override
    public PersonaDTO crear(PersonaRequest request) {
        personaRepository.findByDocumento(request.getDocumento()).ifPresent(p -> {
            throw new BusinessException(
                    messageSource.getMessage("PersonaDocumentoDuplicado", null, Locale.getDefault()));
        });

        Persona persona = personaMapper.toEntity(request);
        Persona saved = personaRepository.save(persona);
        return personaMapper.toDTO(saved);
    }

    @Override
    public PersonaDTO obtenerPorId(Integer id) {
        Persona persona = personaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        messageSource.getMessage("PersonaNotFound", null, Locale.getDefault())));
        return personaMapper.toDTO(persona);
    }

    @Override
    public List<PersonaDTO> obtenerTodas() {
        return personaRepository.findAll().stream()
                .map(personaMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PersonaDTO actualizar(Integer id, PersonaRequest request) {
        Persona persona = personaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        messageSource.getMessage("PersonaNotFound", null, Locale.getDefault())));

        personaRepository.findByDocumento(request.getDocumento())
                .filter(p -> !p.getId().equals(id))
                .ifPresent(p -> {
                    throw new BusinessException(
                            messageSource.getMessage("PersonaDocumentoDuplicado", null, Locale.getDefault()));
                });

        personaMapper.updateEntity(persona, request);
        Persona updated = personaRepository.save(persona);
        return personaMapper.toDTO(updated);
    }

    @Override
    public void eliminar(Integer id) {
        Persona persona = personaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        messageSource.getMessage("PersonaNotFound", null, Locale.getDefault())));
        personaRepository.delete(persona);
    }

}
