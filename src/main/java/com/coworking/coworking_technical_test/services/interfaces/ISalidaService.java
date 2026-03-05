package com.coworking.coworking_technical_test.services.interfaces;

import com.coworking.coworking_technical_test.shared.dto.HistoricoDTO;
import com.coworking.coworking_technical_test.shared.request.SalidaRequest;

public interface ISalidaService {

    HistoricoDTO registrarSalida(SalidaRequest request);

}
