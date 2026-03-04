package com.coworking.coworking_technical_test.services.interfaces;

import java.util.List;

import com.coworking.coworking_technical_test.shared.dto.SedeDTO;
import com.coworking.coworking_technical_test.shared.request.SedeRequest;

public interface ISedeService {

    SedeDTO crear(SedeRequest request);

    SedeDTO obtenerPorId(Integer id);

    List<SedeDTO> obtenerTodas();

    SedeDTO actualizar(Integer id, SedeRequest request);

    void eliminar(Integer id);

    SedeDTO asignarOperador(Integer sedeId, Integer operadorId);

}
