package com.jcaa.usersmanagement.infrastructure.adapter.persistence.mapper;

import com.jcaa.usersmanagement.domain.model.ClienteModel;
import com.jcaa.usersmanagement.domain.valueobject.ClienteEmail;
import com.jcaa.usersmanagement.infrastructure.adapter.persistence.entity.ClienteEntity;

public class ClienteEntityMapper {

    public ClienteEntity toEntity(ClienteModel model) {
        if (model == null) {
            return null;
        }
        return ClienteEntity.builder()
                .numero_identificacion(model.getNumeroIdentificacion())
                .nombre(model.getNombre())
                .primer_apellido(model.getPrimerApellido())
                .segundo_apellido(model.getSegundoApellido())
                .correo_electronico(model.getCorreoElectronico().value())
                .build();
    }

    public ClienteModel toModel(ClienteEntity entity) {
        if (entity == null) {
            return null;
        }
        return ClienteModel.builder()
                .numeroIdentificacion(entity.getNumero_identificacion())
                .nombre(entity.getNombre())
                .primerApellido(entity.getPrimer_apellido())
                .segundoApellido(entity.getSegundo_apellido())
                .correoElectronico(new ClienteEmail(entity.getCorreo_electronico()))
                .build();
    }
}
