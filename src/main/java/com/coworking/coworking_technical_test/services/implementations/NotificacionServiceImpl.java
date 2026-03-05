package com.coworking.coworking_technical_test.services.implementations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.coworking.coworking_technical_test.services.interfaces.INotificacionService;
import com.coworking.coworking_technical_test.shared.request.NotificacionRequest;
import com.coworking.coworking_technical_test.shared.responses.NotificacionResponse;

@Service
public class NotificacionServiceImpl implements INotificacionService {

    private static final Logger log = LoggerFactory.getLogger(NotificacionServiceImpl.class);

    @Override
    public NotificacionResponse enviarNotificacion(NotificacionRequest request) {
        log.info("========== MICROSERVICIO DE NOTIFICACIONES (SIMULADO) ==========");
        log.info("Email destino : {}", request.getEmail());
        log.info("Documento     : {}", request.getDocumento());
        log.info("Sede          : {} (ID: {})", request.getSedeNombre(), request.getSedeId());
        log.info("Mensaje       : {}", request.getMensaje());
        log.info("Estado        : ENVIADO EXITOSAMENTE ");
        log.info("=================================================================");

        return NotificacionResponse.builder()
                .exitoso(true)
                .mensaje("Notificacion enviada exitosamente a " + request.getEmail())
                .build();
    }

}
