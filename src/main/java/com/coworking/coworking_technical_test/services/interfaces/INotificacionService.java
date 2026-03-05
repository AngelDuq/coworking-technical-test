package com.coworking.coworking_technical_test.services.interfaces;

import com.coworking.coworking_technical_test.shared.request.NotificacionRequest;
import com.coworking.coworking_technical_test.shared.responses.NotificacionResponse;

public interface INotificacionService {

    NotificacionResponse enviarNotificacion(NotificacionRequest request);

}
