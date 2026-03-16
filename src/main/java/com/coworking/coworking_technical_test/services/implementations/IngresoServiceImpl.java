package com.coworking.coworking_technical_test.services.implementations;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.coworking.coworking_technical_test.entities.Ingreso;
import com.coworking.coworking_technical_test.entities.Persona;
import com.coworking.coworking_technical_test.entities.Sede;
import com.coworking.coworking_technical_test.exceptions.BusinessException;
import com.coworking.coworking_technical_test.exceptions.MessageKey;
import com.coworking.coworking_technical_test.exceptions.NotFoundException;
import com.coworking.coworking_technical_test.mappers.IngresoMapper;
import com.coworking.coworking_technical_test.repositories.IngresoRepository;
import com.coworking.coworking_technical_test.services.interfaces.IIngresoService;
import com.coworking.coworking_technical_test.services.interfaces.IPersonaService;
import com.coworking.coworking_technical_test.services.interfaces.ISedeService;
import com.coworking.coworking_technical_test.shared.dto.IngresoDTO;
import com.coworking.coworking_technical_test.shared.request.IngresoRequest;
import com.coworking.coworking_technical_test.shared.request.PersonaRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IngresoServiceImpl implements IIngresoService {

    private final IngresoRepository ingresoRepository;
        private final IPersonaService personaService;
        private final ISedeService sedeService;
    private final IngresoMapper ingresoMapper;

    @Override
    public IngresoDTO registrarIngreso(IngresoRequest request) {

                PersonaRequest personaRequest = new PersonaRequest();
                personaRequest.setDocumento(request.getDocumento());
                personaRequest.setNombre(request.getNombre());
                personaRequest.setApellido(request.getApellido());
                personaRequest.setEmail(request.getEmail());

                // Buscar o crear persona por documento
                Persona persona = personaService.obtenerOCrearEntidadPorDocumento(personaRequest);

        // Validar que no exista ingreso activo del mismo documento en ninguna sede
                if (ingresoRepository.existsByPersona(persona)) {
                        throw new BusinessException(MessageKey.INGRESO_ACTIVO);
        }

        // Buscar sede
                Sede sede = sedeService.obtenerEntidadPorId(request.getSedeId());

        // Validar capacidad disponible
        long ocupacionActual = ingresoRepository.countBySedeId(sede.getId());
        if (ocupacionActual >= sede.getCapacidadMaxSimultanea()) {
                        throw new BusinessException(MessageKey.SEDE_CAPACIDAD_EXCEDIDA);
        }

        // Registrar ingreso con fecha y hora exacta
        Ingreso ingreso = new Ingreso();
        ingreso.setPersona(persona);
        ingreso.setSede(sede);
        ingreso.setFechaHoraIngreso(LocalDateTime.now());

        Ingreso saved = ingresoRepository.save(ingreso);
        return ingresoMapper.toDTO(saved);
    }

    @Override
    public List<IngresoDTO> obtenerAccesosActuales() {
        return ingresoRepository.findAll().stream()
                .map(ingresoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<IngresoDTO> obtenerAccesosPorSede(String emailOperador) {
                Sede sede = sedeService.obtenerEntidadPorEmailOperador(emailOperador);

        return ingresoRepository.findBySedeId(sede.getId()).stream()
                .map(ingresoMapper::toDTO)
                .collect(Collectors.toList());
    }

        @Override
        public Ingreso obtenerIngresoActivoPorPersona(Persona persona) {
                return ingresoRepository.findByPersona(persona)
                        .orElseThrow(() -> new NotFoundException(MessageKey.INGRESO_NOT_FOUND));
        }

        @Override
        public void eliminarIngreso(Ingreso ingreso) {
                ingresoRepository.delete(ingreso);
        }

        @Override
        public List<Ingreso> obtenerPersonasPrimerIngreso() {
                return ingresoRepository.findPersonasPrimerIngreso();
        }

}
