package com.coworking.coworking_technical_test.services.interfaces;

import com.coworking.coworking_technical_test.shared.dto.UsuarioDTO;
import com.coworking.coworking_technical_test.shared.request.CrearOperadorRequest;

public interface IUsuarioService {

    UsuarioDTO crearOperador(CrearOperadorRequest request);

}
