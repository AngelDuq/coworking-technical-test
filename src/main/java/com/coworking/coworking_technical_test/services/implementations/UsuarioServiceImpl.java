package com.coworking.coworking_technical_test.services.implementations;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.coworking.coworking_technical_test.entities.Rol;
import com.coworking.coworking_technical_test.entities.Usuario;
import com.coworking.coworking_technical_test.exceptions.BusinessException;
import com.coworking.coworking_technical_test.exceptions.NotFoundException;
import com.coworking.coworking_technical_test.mappers.UsuarioMapper;
import com.coworking.coworking_technical_test.repositories.RolRepository;
import com.coworking.coworking_technical_test.repositories.UsuarioRepository;
import com.coworking.coworking_technical_test.services.interfaces.IUsuarioService;
import com.coworking.coworking_technical_test.shared.dto.UsuarioDTO;
import com.coworking.coworking_technical_test.shared.request.CrearOperadorRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements IUsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final UsuarioMapper usuarioMapper;
    private final PasswordEncoder passwordEncoder;
    private final MessageSource messageSource;

    @Override
    public UsuarioDTO crearOperador(CrearOperadorRequest request) {

        if (usuarioRepository.existsByEmail(request.getEmail())) {
            throw new BusinessException(
                    messageSource.getMessage("UsuarioEmailDuplicado", null, Locale.getDefault()));
        }

        if (usuarioRepository.existsByDocumento(request.getDocumento())) {
            throw new BusinessException(
                    messageSource.getMessage("UsuarioDocumentoDuplicado", null, Locale.getDefault()));
        }

        Rol rolOperador = rolRepository.findByDescripcion("OPERADOR")
                .orElseThrow(() -> new NotFoundException(
                        messageSource.getMessage("RolNotFound", null, Locale.getDefault())));

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

}
