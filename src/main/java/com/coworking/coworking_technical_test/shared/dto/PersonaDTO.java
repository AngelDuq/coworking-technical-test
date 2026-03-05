package com.coworking.coworking_technical_test.shared.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonaDTO {

    private Integer id;
    private String documento;
    private String nombre;
    private String apellido;
    private String email;

}
