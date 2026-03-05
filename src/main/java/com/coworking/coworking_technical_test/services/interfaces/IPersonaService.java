package com.coworking.coworking_technical_test.services.interfaces;

import java.util.List;

import com.coworking.coworking_technical_test.shared.dto.PersonaDTO;
import com.coworking.coworking_technical_test.shared.request.PersonaRequest;

public interface IPersonaService {

    PersonaDTO crear(PersonaRequest request);

    PersonaDTO obtenerPorId(Integer id);

    List<PersonaDTO> obtenerTodas();

    PersonaDTO actualizar(Integer id, PersonaRequest request);

    void eliminar(Integer id);

}
