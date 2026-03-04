package com.coworking.coworking_technical_test.shared.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO {

    private Integer id;
    private String nombre;
    private String apellido;
    private String documento;
    private String email;
    private String rol;

}
