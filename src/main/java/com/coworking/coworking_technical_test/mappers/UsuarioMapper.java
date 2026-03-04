package com.coworking.coworking_technical_test.mappers;

import com.coworking.coworking_technical_test.entities.Usuario;
import com.coworking.coworking_technical_test.shared.dto.UsuarioDTO;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

    public UsuarioDTO toDTO(Usuario usuario) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(usuario.getId());
        dto.setNombre(usuario.getNombre());
        dto.setApellido(usuario.getApellido());
        dto.setDocumento(usuario.getDocumento());
        dto.setEmail(usuario.getEmail());
        if (usuario.getRol() != null) {
            dto.setRol(usuario.getRol().getDescripcion());
        }
        return dto;
    }

}
