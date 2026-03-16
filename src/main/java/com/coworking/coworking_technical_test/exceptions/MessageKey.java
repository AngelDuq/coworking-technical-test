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
    NOTIFICACION_ENVIADA_EXITOSA("NotificacionEnviadaExitosa");

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
