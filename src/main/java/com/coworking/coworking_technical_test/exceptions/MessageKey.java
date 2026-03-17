package com.coworking.coworking_technical_test.exceptions;

public enum MessageKey {

    SEDE_NOT_FOUND("SedeNotFound"),
    SEDE_CAPACIDAD_EXCEDIDA("SedeCapacidadExcedida"),
    HISTORIAL_NOT_FOUND("HistorialNotFound"),
    PERSONA_NOT_FOUND("PersonaNotFound"),
    PERSONA_DOCUMENTO_DUPLICADO("PersonaDocumentoDuplicado"),
    INGRESO_NOT_FOUND("IngresoNotFound"),
    INGRESO_ACTIVO("IngresoActivo"),
    USUARIO_NOT_FOUND("UsuarioNotFound"),
    USUARIO_EMAIL_DUPLICADO("UsuarioEmailDuplicado"),
    USUARIO_DOCUMENTO_DUPLICADO("UsuarioDocumentoDuplicado"),
    CUPON_NOT_FOUND("CuponNotFound"),
    CUPON_EXPIRADO("CuponExpirado"),
    CUPON_UTILIZADO("CuponUtilizado"),
    ROL_NOT_FOUND("RolNotFound"),
    USUARIO_NO_ES_OPERADOR("UsuarioNoEsOperador"),
    CREDENCIALES_INVALIDAS("CredencialesInvalidas"),
    TOKEN_BLACKLISTED("TokenBlacklisted"),
    CUPON_REDIMIDO_EXITOSO("CuponRedimidoExitoso"),
    CUPON_FIDELIDAD_NOTIFICACION("CuponFidelidadNotificacion"),
    NOTIFICACION_ENVIADA_EXITOSA("NotificacionEnviadaExitosa"),
    LOG_CUPON_FIDELIDAD_GENERADO("LogCuponFidelidadGenerado"),
    LOG_CUPONES_VENCIDOS_NO_ENCONTRADOS("LogCuponesVencidosNoEncontrados"),
    LOG_CUPONES_VENCIDOS_EXPIRADOS("LogCuponesVencidosExpirados"),
    LOG_CUPON_REDIMIDO_EXITOSO("LogCuponRedimidoExitoso"),
    LOG_CUPON_SCHEDULER_INICIO("LogCuponSchedulerInicio"),
    LOG_CUPON_SCHEDULER_FIN("LogCuponSchedulerFin"),
    LOG_NOTIFICACION_HEADER("LogNotificacionHeader"),
    LOG_NOTIFICACION_EMAIL_DESTINO("LogNotificacionEmailDestino"),
    LOG_NOTIFICACION_DOCUMENTO("LogNotificacionDocumento"),
    LOG_NOTIFICACION_SEDE("LogNotificacionSede"),
    LOG_NOTIFICACION_MENSAJE("LogNotificacionMensaje"),
    LOG_NOTIFICACION_ESTADO("LogNotificacionEstado"),
    LOG_NOTIFICACION_FOOTER("LogNotificacionFooter"),
    NOTIFICACION_ENDPOINT_PATH("NotificacionEndpointPath"),
    NOTIFICACION_RESPUESTA_INVALIDA("NotificacionRespuestaInvalida"),
    NOTIFICACION_ENVIO_FALLIDO("NotificacionEnvioFallido"),
    LOG_NOTIFICACION_ERROR_CONSUMO("LogNotificacionErrorConsumo");

    private final String key;

    MessageKey(String key) {
        this.key = key;
    }

    public String key() {
        return key;
    }

    public static MessageKey fromKey(String rawKey) {
        for (MessageKey value : values()) {
            if (value.key.equals(rawKey)) {
                return value;
            }
        }
        return null;
    }
}
