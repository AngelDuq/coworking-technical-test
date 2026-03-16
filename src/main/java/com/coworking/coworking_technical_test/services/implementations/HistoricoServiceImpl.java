package com.coworking.coworking_technical_test.services.implementations;

import org.springframework.stereotype.Service;

import com.coworking.coworking_technical_test.repositories.HistoricoRepository;
import com.coworking.coworking_technical_test.services.interfaces.IHistoricoService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HistoricoServiceImpl implements IHistoricoService {

    private final HistoricoRepository historicoRepository;

    @Override
    public long obtenerMinutosPermanenciaPorPersonaYSede(Integer personaId, Integer sedeId) {
        Long minutos = historicoRepository.sumarMinutosPermanenciaPorPersonaYSede(personaId, sedeId);
        return minutos != null ? minutos : 0L;
    }

}
