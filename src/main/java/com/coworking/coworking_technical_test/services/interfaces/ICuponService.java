package com.coworking.coworking_technical_test.services.interfaces;

import com.coworking.coworking_technical_test.entities.Persona;
import com.coworking.coworking_technical_test.entities.Sede;

public interface ICuponService {

    void verificarYGenerarCupon(Persona persona, Sede sede);

    void expirarCuponesVencidos();

}
