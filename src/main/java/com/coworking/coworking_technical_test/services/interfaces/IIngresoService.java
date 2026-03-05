package com.coworking.coworking_technical_test.services.interfaces;

import java.util.List;

import com.coworking.coworking_technical_test.shared.dto.IngresoDTO;
import com.coworking.coworking_technical_test.shared.request.IngresoRequest;

public interface IIngresoService {

    IngresoDTO registrarIngreso(IngresoRequest request);

    List<IngresoDTO> obtenerAccesosActuales();

    List<IngresoDTO> obtenerAccesosPorSede(String emailOperador);

}
