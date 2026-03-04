package com.coworking.coworking_technical_test.services.implementations;

import java.time.LocalDateTime;
import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.coworking.coworking_technical_test.entities.Ingreso;
import com.coworking.coworking_technical_test.entities.Persona;
import com.coworking.coworking_technical_test.entities.Sede;
import com.coworking.coworking_technical_test.exceptions.BusinessException;
import com.coworking.coworking_technical_test.exceptions.NotFoundException;
import com.coworking.coworking_technical_test.mappers.IngresoMapper;
import com.coworking.coworking_technical_test.repositories.IngresoRepository;
import com.coworking.coworking_technical_test.repositories.PersonaRepository;
import com.coworking.coworking_technical_test.repositories.SedeRepository;
import com.coworking.coworking_technical_test.services.interfaces.IIngresoService;
import com.coworking.coworking_technical_test.shared.dto.IngresoDTO;
import com.coworking.coworking_technical_test.shared.request.IngresoRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IngresoServiceImpl implements IIngresoService {

    private final IngresoRepository ingresoRepository;
    private final PersonaRepository personaRepository;
    private final SedeRepository sedeRepository;
    private final IngresoMapper ingresoMapper;
    private final MessageSource messageSource;

    @Override
    public IngresoDTO registrarIngreso(IngresoRequest request) {

        // Buscar o crear persona por documento
        Persona persona = personaRepository.findByDocumento(request.getDocumento())
                .orElseGet(() -> {
                    Persona nueva = new Persona();
                    nueva.setDocumento(request.getDocumento());
                    nueva.setNombre(request.getNombre());
                    nueva.setApellido(request.getApellido());
                    return personaRepository.save(nueva);
                });

        // Validar que no exista ingreso activo del mismo documento en ninguna sede
        if (ingresoRepository.existsByPersona(persona)) {
            throw new BusinessException(
                    messageSource.getMessage("IngresoActivo", null, Locale.getDefault()));
        }

        // Buscar sede
        Sede sede = sedeRepository.findById(request.getSedeId())
                .orElseThrow(() -> new NotFoundException(
                        messageSource.getMessage("SedeNotFound", null, Locale.getDefault())));

        // Validar capacidad disponible
        long ocupacionActual = ingresoRepository.countBySedeId(sede.getId());
        if (ocupacionActual >= sede.getCapacidadMaxSimultanea()) {
            throw new BusinessException(
                    messageSource.getMessage("SedeCapacidadExcedida", null, Locale.getDefault()));
        }

        // Registrar ingreso con fecha y hora exacta
        Ingreso ingreso = new Ingreso();
        ingreso.setPersona(persona);
        ingreso.setSede(sede);
        ingreso.setFechaHoraIngreso(LocalDateTime.now());

        Ingreso saved = ingresoRepository.save(ingreso);
        return ingresoMapper.toDTO(saved);
    }

}
