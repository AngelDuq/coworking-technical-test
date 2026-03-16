package com.coworking.coworking_technical_test.services.implementations;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.coworking.coworking_technical_test.entities.Rol;
import com.coworking.coworking_technical_test.entities.Usuario;
import com.coworking.coworking_technical_test.exceptions.BusinessException;
import com.coworking.coworking_technical_test.exceptions.MessageKey;
import com.coworking.coworking_technical_test.exceptions.NotFoundException;
import com.coworking.coworking_technical_test.mappers.UsuarioMapper;
import com.coworking.coworking_technical_test.repositories.RolRepository;
import com.coworking.coworking_technical_test.repositories.UsuarioRepository;
import com.coworking.coworking_technical_test.services.interfaces.IUsuarioService;
import com.coworking.coworking_technical_test.shared.dto.UsuarioDTO;
import com.coworking.coworking_technical_test.shared.request.ActualizarUsuarioRequest;
import com.coworking.coworking_technical_test.shared.request.CrearOperadorRequest;
import com.coworking.coworking_technical_test.shared.util.Constants;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements IUsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final UsuarioMapper usuarioMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UsuarioDTO crearOperador(CrearOperadorRequest request) {

        if (usuarioRepository.existsByEmail(request.getEmail())) {
            throw new BusinessException(MessageKey.USUARIO_EMAIL_DUPLICADO);
        }

        if (usuarioRepository.existsByDocumento(request.getDocumento())) {
            throw new BusinessException(MessageKey.USUARIO_DOCUMENTO_DUPLICADO);
        }

        Rol rolOperador = rolRepository.findByDescripcion(Constants.ROL_OPERADOR)
            .orElseThrow(() -> new NotFoundException(MessageKey.ROL_NOT_FOUND));

        Usuario usuario = new Usuario();
        usuario.setNombre(request.getNombre());
        usuario.setApellido(request.getApellido());
        usuario.setDocumento(request.getDocumento());
        usuario.setEmail(request.getEmail());
        usuario.setPassword(passwordEncoder.encode(request.getPassword()));
        usuario.setRol(rolOperador);

        Usuario saved = usuarioRepository.save(usuario);
        return usuarioMapper.toDTO(saved);
    }

    @Override
    public UsuarioDTO obtenerPorId(Integer id) {
        Usuario usuario = usuarioRepository.findById(id)
            .orElseThrow(() -> new NotFoundException(MessageKey.USUARIO_NOT_FOUND));
        return usuarioMapper.toDTO(usuario);
    }

    @Override
    public List<UsuarioDTO> obtenerTodos() {
        return usuarioRepository.findAll().stream()
                .map(usuarioMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UsuarioDTO actualizar(Integer id, ActualizarUsuarioRequest request) {
        Usuario usuario = usuarioRepository.findById(id)
            .orElseThrow(() -> new NotFoundException(MessageKey.USUARIO_NOT_FOUND));

        if (!usuario.getEmail().equals(request.getEmail()) && usuarioRepository.existsByEmail(request.getEmail())) {
            throw new BusinessException(MessageKey.USUARIO_EMAIL_DUPLICADO);
        }

        if (!usuario.getDocumento().equals(request.getDocumento())
                && usuarioRepository.existsByDocumento(request.getDocumento())) {
            throw new BusinessException(MessageKey.USUARIO_DOCUMENTO_DUPLICADO);
        }

        usuario.setNombre(request.getNombre());
        usuario.setApellido(request.getApellido());
        usuario.setDocumento(request.getDocumento());
        usuario.setEmail(request.getEmail());

        Usuario updated = usuarioRepository.save(usuario);
        return usuarioMapper.toDTO(updated);
    }

    @Override
    public void eliminar(Integer id) {
        Usuario usuario = usuarioRepository.findById(id)
            .orElseThrow(() -> new NotFoundException(MessageKey.USUARIO_NOT_FOUND));
        usuarioRepository.delete(usuario);
    }

}
