package com.coworking.coworking_technical_test.services.interfaces;

import java.util.List;

import com.coworking.coworking_technical_test.shared.dto.UsuarioDTO;
import com.coworking.coworking_technical_test.shared.request.ActualizarUsuarioRequest;
import com.coworking.coworking_technical_test.shared.request.CrearOperadorRequest;

public interface IUsuarioService {

    UsuarioDTO crearOperador(CrearOperadorRequest request);

    UsuarioDTO obtenerPorId(Integer id);

    List<UsuarioDTO> obtenerTodos();

    UsuarioDTO actualizar(Integer id, ActualizarUsuarioRequest request);

    void eliminar(Integer id);

}
