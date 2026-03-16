package com.coworking.coworking_technical_test.services.implementations;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.coworking.coworking_technical_test.entities.Sede;
import com.coworking.coworking_technical_test.entities.Usuario;
import com.coworking.coworking_technical_test.exceptions.BusinessException;
import com.coworking.coworking_technical_test.exceptions.MessageKey;
import com.coworking.coworking_technical_test.exceptions.NotFoundException;
import com.coworking.coworking_technical_test.mappers.SedeMapper;
import com.coworking.coworking_technical_test.repositories.SedeRepository;
import com.coworking.coworking_technical_test.services.interfaces.ISedeService;
import com.coworking.coworking_technical_test.services.interfaces.IUsuarioService;
import com.coworking.coworking_technical_test.shared.dto.SedeDTO;
import com.coworking.coworking_technical_test.shared.request.SedeRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SedeServiceImpl implements ISedeService {

    private final SedeRepository sedeRepository;
    private final IUsuarioService usuarioService;
    private final SedeMapper sedeMapper;

    @Override
    public SedeDTO crear(SedeRequest request) {
        Sede sede = sedeMapper.toEntity(request);
        Sede sedeSaved = sedeRepository.save(sede);
        return sedeMapper.toDTO(sedeSaved);
    }

    @Override
    public SedeDTO obtenerPorId(Integer id) {
        Sede sede = sedeRepository.findById(id)
            .orElseThrow(() -> new NotFoundException(MessageKey.SEDE_NOT_FOUND));
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
            .orElseThrow(() -> new NotFoundException(MessageKey.SEDE_NOT_FOUND));

        sedeMapper.updateEntity(sede, request);
        Sede sedeUpdated = sedeRepository.save(sede);
        return sedeMapper.toDTO(sedeUpdated);
    }

    @Override
    public void eliminar(Integer id) {
        Sede sede = sedeRepository.findById(id)
            .orElseThrow(() -> new NotFoundException(MessageKey.SEDE_NOT_FOUND));
        sedeRepository.delete(sede);
    }

    @Override
    public SedeDTO asignarOperador(Integer sedeId, Integer operadorId) {
        Sede sede = obtenerEntidadPorId(sedeId);
        Usuario operador = usuarioService.obtenerEntidadPorId(operadorId);

        // Validar que el usuario tenga rol OPERADOR
        if (!usuarioService.esOperador(operador)) {
            throw new BusinessException(MessageKey.USUARIO_NO_ES_OPERADOR);
        }

        sede.setOperador(operador);
        Sede sedeUpdated = sedeRepository.save(sede);
        return sedeMapper.toDTO(sedeUpdated);
    }

    @Override
    public Sede obtenerEntidadPorId(Integer id) {
        return sedeRepository.findById(id)
            .orElseThrow(() -> new NotFoundException(MessageKey.SEDE_NOT_FOUND));
    }

    @Override
    public Sede obtenerEntidadPorEmailOperador(String emailOperador) {
        Usuario operador = usuarioService.obtenerEntidadPorEmail(emailOperador);
        return sedeRepository.findByOperadorId(operador.getId())
            .orElseThrow(() -> new NotFoundException(MessageKey.SEDE_NOT_FOUND));
    }

}
