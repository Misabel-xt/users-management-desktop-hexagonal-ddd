package com.jcaa.usersmanagement.infrastructure.adapter.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClienteEntity {
    private String numero_identificacion;
    private String nombre;
    private String primer_apellido;
    private String segundo_apellido;
    private String correo_electronico;
}