package com.coworking.coworking_technical_test.mappers;

import org.springframework.stereotype.Component;

import com.coworking.coworking_technical_test.entities.Cupon;
import com.coworking.coworking_technical_test.entities.Cupon.EstadoCupon;
import com.coworking.coworking_technical_test.shared.responses.CuponRedencionResponse;

@Component
public class CuponMapper {

    public CuponRedencionResponse toRedencionResponse(Cupon cupon, EstadoCupon estadoAnterior, String mensaje) {
        CuponRedencionResponse response = new CuponRedencionResponse();
        response.setCuponId(cupon.getId());
        response.setEstadoAnterior(estadoAnterior.name());
        response.setEstadoActual(cupon.getEstado().name());
        response.setFechaUso(cupon.getFechaUso());
        response.setFechaVencimiento(cupon.getFechaVencimiento());
        response.setSedeNombre(cupon.getSede().getNombre());
        response.setPersonaDocumento(cupon.getPersona().getDocumento());
        response.setPersonaNombre(cupon.getPersona().getNombre() + " " + cupon.getPersona().getApellido());
        response.setMensaje(mensaje);
        return response;
    }
}
