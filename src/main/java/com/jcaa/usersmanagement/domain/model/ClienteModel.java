package com.jcaa.usersmanagement.domain.model;

import com.jcaa.usersmanagement.domain.valueobject.ClienteEmail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClienteModel {
    private String numeroIdentificacion; // Este también debería ser un VO en el futuro
    private String nombre;
    private ClienteEmail correoElectronico;
    private String primerApellido;
    private String segundoApellido;
}
