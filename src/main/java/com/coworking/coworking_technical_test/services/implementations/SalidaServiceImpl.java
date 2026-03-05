package com.coworking.coworking_technical_test.services.implementations;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.coworking.coworking_technical_test.entities.Historico;
import com.coworking.coworking_technical_test.entities.Ingreso;
import com.coworking.coworking_technical_test.entities.Persona;
import com.coworking.coworking_technical_test.exceptions.NotFoundException;
import com.coworking.coworking_technical_test.mappers.HistoricoMapper;
import com.coworking.coworking_technical_test.repositories.HistoricoRepository;
import com.coworking.coworking_technical_test.repositories.IngresoRepository;
import com.coworking.coworking_technical_test.repositories.PersonaRepository;
import com.coworking.coworking_technical_test.services.interfaces.ICuponService;
import com.coworking.coworking_technical_test.services.interfaces.ISalidaService;
import com.coworking.coworking_technical_test.shared.dto.HistoricoDTO;
import com.coworking.coworking_technical_test.shared.request.SalidaRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SalidaServiceImpl implements ISalidaService {

    private final IngresoRepository ingresoRepository;
    private final HistoricoRepository historicoRepository;
    private final PersonaRepository personaRepository;
    private final HistoricoMapper historicoMapper;
    private final MessageSource messageSource;
    private final ICuponService cuponService;

    @Override
    @Transactional
    public HistoricoDTO registrarSalida(SalidaRequest request) {

        // Buscar persona por documento
        Persona persona = personaRepository.findByDocumento(request.getDocumento())
                .orElseThrow(() -> new NotFoundException(
                        messageSource.getMessage("PersonaNotFound", null, Locale.getDefault())));

        // Validar que exista ingreso activo
        Ingreso ingreso = ingresoRepository.findByPersona(persona)
                .orElseThrow(() -> new NotFoundException(
                        messageSource.getMessage("IngresoNotFound", null, Locale.getDefault())));

        // Registrar fecha y hora de salida
        LocalDateTime fechaSalida = LocalDateTime.now();

        // Calcular valor a pagar según tiempo de permanencia
        Duration duracion = Duration.between(ingreso.getFechaHoraIngreso(), fechaSalida);
        long minutosTotales = duracion.toMinutes();
        // Redondear hacia arriba: cualquier fracción de hora cuenta como hora completa
        long horasCobradas = (minutosTotales + 59) / 60;
        if (horasCobradas < 1) {
            horasCobradas = 1; // Mínimo 1 hora
        }

        BigDecimal valorPagar = ingreso.getSede().getPrecioHora()
                .multiply(BigDecimal.valueOf(horasCobradas))
                .setScale(2, RoundingMode.HALF_UP);

        // Crear registro en histórico con la información del ingreso
        Historico historico = new Historico();
        historico.setPersona(ingreso.getPersona());
        historico.setSede(ingreso.getSede());
        historico.setFechaHoraIngreso(ingreso.getFechaHoraIngreso());
        historico.setFechaHoraSalida(fechaSalida);
        historico.setValorPagar(valorPagar);

        Historico saved = historicoRepository.save(historico);

        // Eliminar el registro de ingreso (liberar cupo en la sede)
        ingresoRepository.delete(ingreso);

        // Verificar fidelidad y generar cupón si aplica (+20 horas en la misma sede)
        cuponService.verificarYGenerarCupon(persona, ingreso.getSede());

        return historicoMapper.toDTO(saved);
    }

}
