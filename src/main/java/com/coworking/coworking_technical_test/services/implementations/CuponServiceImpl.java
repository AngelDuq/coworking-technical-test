package com.coworking.coworking_technical_test.services.implementations;

import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.coworking.coworking_technical_test.entities.Cupon;
import com.coworking.coworking_technical_test.entities.Cupon.EstadoCupon;
import com.coworking.coworking_technical_test.entities.Persona;
import com.coworking.coworking_technical_test.entities.Sede;
import com.coworking.coworking_technical_test.exceptions.BusinessException;
import com.coworking.coworking_technical_test.exceptions.MessageKey;
import com.coworking.coworking_technical_test.exceptions.MessageResolver;
import com.coworking.coworking_technical_test.exceptions.NotFoundException;
import com.coworking.coworking_technical_test.mappers.CuponMapper;
import com.coworking.coworking_technical_test.repositories.CuponRepository;
import com.coworking.coworking_technical_test.services.interfaces.ICuponService;
import com.coworking.coworking_technical_test.services.interfaces.IHistoricoService;
import com.coworking.coworking_technical_test.services.interfaces.INotificacionService;
import com.coworking.coworking_technical_test.shared.request.NotificacionRequest;
import com.coworking.coworking_technical_test.shared.responses.CuponRedencionResponse;
import com.coworking.coworking_technical_test.shared.util.Constants;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CuponServiceImpl implements ICuponService {

    private static final Logger log = LoggerFactory.getLogger(CuponServiceImpl.class);
    private static final long HORAS_MINIMAS_FIDELIDAD = 20;
    private static final int DIAS_VIGENCIA_CUPON = 10;

    private final CuponRepository cuponRepository;
    private final IHistoricoService historicoService;
    private final INotificacionService notificacionService;
    private final CuponMapper cuponMapper;
    private final MessageResolver messageResolver;

    @Override
    public void verificarYGenerarCupon(Persona persona, Sede sede) {
        // Verificar si ya tiene cupón para esta sede (una única vez)
        if (cuponRepository.existsByPersonaIdAndSedeId(persona.getId(), sede.getId())) {
            return;
        }

        // Sumar minutos acumulados en esta sede
        long minutosTotales = historicoService
            .obtenerMinutosPermanenciaPorPersonaYSede(persona.getId(), sede.getId());
        long horasAcumuladas = minutosTotales / 60;

        if (horasAcumuladas < HORAS_MINIMAS_FIDELIDAD) {
            return;
        }

        // Generar cupón
        LocalDate hoy = LocalDate.now();
        Cupon cupon = new Cupon();
        cupon.setPersona(persona);
        cupon.setSede(sede);
        cupon.setFechaGeneracion(hoy);
        cupon.setFechaVencimiento(hoy.plusDays(DIAS_VIGENCIA_CUPON));
        cupon.setEstado(EstadoCupon.ACTIVO);
        cuponRepository.save(cupon);

        log.info(messageResolver.get(
            MessageKey.LOG_CUPON_FIDELIDAD_GENERADO,
            persona.getDocumento(),
            sede.getNombre()));

        // Enviar notificación simulada
        String mensaje = messageResolver.get(
            MessageKey.CUPON_FIDELIDAD_NOTIFICACION,
            HORAS_MINIMAS_FIDELIDAD,
            sede.getNombre(),
            cupon.getFechaVencimiento());

        NotificacionRequest notificacion = NotificacionRequest.builder()
                .email(persona.getEmail() != null ? persona.getEmail() : Constants.EMAIL_DEFAULT)
                .documento(persona.getDocumento())
                .mensaje(mensaje)
                .sedeId(sede.getId())
                .sedeNombre(sede.getNombre())
                .build();

        notificacionService.enviarNotificacion(notificacion);
    }

    @Override
    @Transactional
    public void expirarCuponesVencidos() {
        LocalDate hoy = LocalDate.now();
        List<Cupon> cuponesVencidos = cuponRepository
                .findByEstadoAndFechaVencimientoBefore(EstadoCupon.ACTIVO, hoy);

        if (cuponesVencidos.isEmpty()) {
            log.info(messageResolver.get(MessageKey.LOG_CUPONES_VENCIDOS_NO_ENCONTRADOS));
            return;
        }

        for (Cupon cupon : cuponesVencidos) {
            cupon.setEstado(EstadoCupon.EXPIRADO);
        }
        cuponRepository.saveAll(cuponesVencidos);

        log.info(messageResolver.get(MessageKey.LOG_CUPONES_VENCIDOS_EXPIRADOS, cuponesVencidos.size()));
    }

    @Override
    @Transactional
    public CuponRedencionResponse redimirCupon(Integer cuponId) {
        Cupon cupon = cuponRepository.findById(cuponId)
                .orElseThrow(() -> new NotFoundException(MessageKey.CUPON_NOT_FOUND));

        if (cupon.getEstado() != EstadoCupon.ACTIVO) {
            if (cupon.getEstado() == EstadoCupon.EXPIRADO) {
                throw new BusinessException(MessageKey.CUPON_EXPIRADO);
            }
            throw new BusinessException(MessageKey.CUPON_UTILIZADO);
        }

        EstadoCupon estadoAnterior = cupon.getEstado();
        cupon.setEstado(EstadoCupon.UTILIZADO);
        cupon.setFechaUso(LocalDate.now());
        cuponRepository.save(cupon);

        log.info(messageResolver.get(MessageKey.LOG_CUPON_REDIMIDO_EXITOSO, cuponId));

        return cuponMapper.toRedencionResponse(
            cupon,
            estadoAnterior,
            messageResolver.get(MessageKey.CUPON_REDIMIDO_EXITOSO));
    }

}
