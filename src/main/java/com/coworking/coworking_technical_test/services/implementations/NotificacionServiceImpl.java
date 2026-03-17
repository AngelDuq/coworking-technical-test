package com.coworking.coworking_technical_test.services.implementations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.coworking.coworking_technical_test.exceptions.MessageKey;
import com.coworking.coworking_technical_test.exceptions.MessageResolver;
import com.coworking.coworking_technical_test.services.interfaces.INotificacionService;
import com.coworking.coworking_technical_test.shared.request.NotificacionRequest;
import com.coworking.coworking_technical_test.shared.responses.NotificacionResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificacionServiceImpl implements INotificacionService {

    private static final Logger log = LoggerFactory.getLogger(NotificacionServiceImpl.class);
    private final RestClient restClient;
    private final MessageResolver messageResolver;

    @Value("${notification.service.base-url}")
    private String notificationServiceBaseUrl;

    @Override
    public NotificacionResponse enviarNotificacion(NotificacionRequest request) {
        try {
            String endpointPath = messageResolver.get(MessageKey.NOTIFICACION_ENDPOINT_PATH);

            log.info(messageResolver.get(MessageKey.LOG_NOTIFICACION_HEADER));
            log.info(messageResolver.get(MessageKey.LOG_NOTIFICACION_EMAIL_DESTINO, request.getEmail()));
            log.info(messageResolver.get(MessageKey.LOG_NOTIFICACION_DOCUMENTO, request.getDocumento()));
            log.info(messageResolver.get(MessageKey.LOG_NOTIFICACION_SEDE, request.getSedeNombre(), request.getSedeId()));
            log.info(messageResolver.get(MessageKey.LOG_NOTIFICACION_MENSAJE, request.getMensaje()));

            NotificacionResponse response = restClient.post()
                    .uri(notificationServiceBaseUrl + endpointPath)
                    .body(request)
                    .retrieve()
                    .body(NotificacionResponse.class);

            if (response == null) {
                return NotificacionResponse.builder()
                        .exitoso(false)
                        .mensaje(messageResolver.get(MessageKey.NOTIFICACION_RESPUESTA_INVALIDA))
                        .build();
            }

            log.info(messageResolver.get(MessageKey.LOG_NOTIFICACION_ESTADO));
            log.info(messageResolver.get(MessageKey.LOG_NOTIFICACION_FOOTER));

            return response;
        } catch (Exception ex) {
            log.error(messageResolver.get(MessageKey.LOG_NOTIFICACION_ERROR_CONSUMO, ex.getMessage()), ex);
            return NotificacionResponse.builder()
                    .exitoso(false)
                    .mensaje(messageResolver.get(MessageKey.NOTIFICACION_ENVIO_FALLIDO))
                    .build();
        }
    }

}
