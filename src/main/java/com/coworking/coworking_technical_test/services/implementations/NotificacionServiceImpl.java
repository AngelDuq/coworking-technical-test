package com.coworking.coworking_technical_test.services.implementations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.coworking.coworking_technical_test.services.interfaces.INotificacionService;
import com.coworking.coworking_technical_test.shared.request.NotificacionRequest;
import com.coworking.coworking_technical_test.shared.responses.NotificacionResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificacionServiceImpl implements INotificacionService {

    private static final Logger log = LoggerFactory.getLogger(NotificacionServiceImpl.class);
    private final RestClient restClient;

    @Value("${notification.service.base-url}")
    private String notificationServiceBaseUrl;

    @Override
    public NotificacionResponse enviarNotificacion(NotificacionRequest request) {
        try {
            NotificacionResponse response = restClient.post()
                    .uri(notificationServiceBaseUrl + "/api/notificaciones")
                    .body(request)
                    .retrieve()
                    .body(NotificacionResponse.class);

            if (response == null) {
                return NotificacionResponse.builder()
                        .exitoso(false)
                        .mensaje("El microservicio de notificaciones no devolvio una respuesta valida.")
                        .build();
            }

            return response;
        } catch (Exception ex) {
            log.error("Error consumiendo el microservicio de notificaciones: {}", ex.getMessage(), ex);
            return NotificacionResponse.builder()
                    .exitoso(false)
                    .mensaje("No fue posible enviar la notificacion al microservicio.")
                    .build();
        }
    }

}
