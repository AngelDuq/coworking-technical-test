package com.coworking.coworking_technical_test.services.implementations;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.coworking.coworking_technical_test.entities.Sede;
import com.coworking.coworking_technical_test.entities.Usuario;
import com.coworking.coworking_technical_test.exceptions.BusinessException;
import com.coworking.coworking_technical_test.exceptions.NotFoundException;
import com.coworking.coworking_technical_test.mappers.SedeMapper;
import com.coworking.coworking_technical_test.repositories.SedeRepository;
import com.coworking.coworking_technical_test.repositories.UsuarioRepository;
import com.coworking.coworking_technical_test.services.interfaces.ISedeService;
import com.coworking.coworking_technical_test.shared.dto.SedeDTO;
import com.coworking.coworking_technical_test.shared.request.SedeRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SedeServiceImpl implements ISedeService {

    private final SedeRepository sedeRepository;
    private final UsuarioRepository usuarioRepository;
    private final SedeMapper sedeMapper;
    private final MessageSource messageSource;

    @Override
    public SedeDTO crear(SedeRequest request) {
        Sede sede = sedeMapper.toEntity(request);
        Sede sedeSaved = sedeRepository.save(sede);
        return sedeMapper.toDTO(sedeSaved);
    }

    @Override
    public SedeDTO obtenerPorId(Integer id) {
        Sede sede = sedeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        messageSource.getMessage("SedeNotFound", null, Locale.getDefault())));
        return sedeMapper.toDTO(sede);
    }

    @Override
    public List<SedeDTO> obtenerTodas() {
        return sedeRepository.findAll().stream()
                .map(sedeMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public SedeDTO actualizar(Integer id, SedeRequest request) {
        Sede sede = sedeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        messageSource.getMessage("SedeNotFound", null, Locale.getDefault())));

        sedeMapper.updateEntity(sede, request);
        Sede sedeUpdated = sedeRepository.save(sede);
        return sedeMapper.toDTO(sedeUpdated);
    }

    @Override
    public void eliminar(Integer id) {
        Sede sede = sedeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        messageSource.getMessage("SedeNotFound", null, Locale.getDefault())));
        sedeRepository.delete(sede);
    }

    @Override
    public SedeDTO asignarOperador(Integer sedeId, Integer operadorId) {
        Sede sede = sedeRepository.findById(sedeId)
                .orElseThrow(() -> new NotFoundException(
                        messageSource.getMessage("SedeNotFound", null, Locale.getDefault())));

        Usuario operador = usuarioRepository.findById(operadorId)
                .orElseThrow(() -> new NotFoundException(
                        messageSource.getMessage("UsuarioNotFound", null, Locale.getDefault())));

        // Validar que el usuario tenga rol OPERADOR
        if (operador.getRol() == null || !"OPERADOR".equalsIgnoreCase(operador.getRol().getDescripcion())) {
            throw new BusinessException("El usuario seleccionado no tiene el rol OPERADOR.");
        }

        sede.setOperador(operador);
        Sede sedeUpdated = sedeRepository.save(sede);
        return sedeMapper.toDTO(sedeUpdated);
    }

}
