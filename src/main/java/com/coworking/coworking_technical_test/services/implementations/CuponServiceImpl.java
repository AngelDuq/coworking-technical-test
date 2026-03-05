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
import com.coworking.coworking_technical_test.repositories.CuponRepository;
import com.coworking.coworking_technical_test.repositories.HistoricoRepository;
import com.coworking.coworking_technical_test.services.interfaces.ICuponService;
import com.coworking.coworking_technical_test.services.interfaces.INotificacionService;
import com.coworking.coworking_technical_test.shared.request.NotificacionRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CuponServiceImpl implements ICuponService {

    private static final Logger log = LoggerFactory.getLogger(CuponServiceImpl.class);
    private static final long HORAS_MINIMAS_FIDELIDAD = 20;
    private static final int DIAS_VIGENCIA_CUPON = 10;

    private final HistoricoRepository historicoRepository;
    private final CuponRepository cuponRepository;
    private final INotificacionService notificacionService;

    @Override
    public void verificarYGenerarCupon(Persona persona, Sede sede) {
        // Verificar si ya tiene cupón para esta sede (una única vez)
        if (cuponRepository.existsByPersonaIdAndSedeId(persona.getId(), sede.getId())) {
            return;
        }

        // Sumar minutos acumulados en esta sede
        Long minutosTotales = historicoRepository
                .sumarMinutosPermanenciaPorPersonaYSede(persona.getId(), sede.getId());
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

        log.info("Cupon de fidelidad generado para persona {} en sede {}", persona.getDocumento(), sede.getNombre());

        // Enviar notificación simulada
        String mensaje = String.format(
                "Gracias por tu fidelidad! Has acumulado mas de %d horas en la sede %s. "
                        + "Se te ha otorgado un cupon de consumo interno con vigencia hasta el %s.",
                HORAS_MINIMAS_FIDELIDAD, sede.getNombre(), cupon.getFechaVencimiento());

        NotificacionRequest notificacion = NotificacionRequest.builder()
                .email(persona.getEmail() != null ? persona.getEmail() : "sin-email@coworking.com")
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
            log.info("No se encontraron cupones activos vencidos para expirar.");
            return;
        }

        for (Cupon cupon : cuponesVencidos) {
            cupon.setEstado(EstadoCupon.EXPIRADO);
        }
        cuponRepository.saveAll(cuponesVencidos);

        log.info("Se expiraron {} cupones vencidos.", cuponesVencidos.size());
    }

}
