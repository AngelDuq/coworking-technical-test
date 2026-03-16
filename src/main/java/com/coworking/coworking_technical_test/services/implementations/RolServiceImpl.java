package com.coworking.coworking_technical_test.services.implementations;

import org.springframework.stereotype.Service;

import com.coworking.coworking_technical_test.entities.Rol;
import com.coworking.coworking_technical_test.exceptions.MessageKey;
import com.coworking.coworking_technical_test.exceptions.NotFoundException;
import com.coworking.coworking_technical_test.repositories.RolRepository;
import com.coworking.coworking_technical_test.services.interfaces.IRolService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RolServiceImpl implements IRolService {

    private final RolRepository rolRepository;

    @Override
    public Rol obtenerPorDescripcion(String descripcion) {
        return rolRepository.findByDescripcion(descripcion)
                .orElseThrow(() -> new NotFoundException(MessageKey.ROL_NOT_FOUND));
    }

}
