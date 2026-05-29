package com.jcaa.usersmanagement.application.service.dto.command;

import com.jcaa.usersmanagement.application.port.out.ClienteRepositoryPort;
import com.jcaa.usersmanagement.application.service.dto.command.CreateClienteCommand;
import com.jcaa.usersmanagement.domain.model.ClienteModel;
import com.jcaa.usersmanagement.domain.valueobject.ClienteEmail;
// Importa los otros Value Objects

public class CreateClienteService {

    private final ClienteRepositoryPort repositoryPort;

    public CreateClienteService(ClienteRepositoryPort repositoryPort) {
        this.repositoryPort = repositoryPort;
    }

    public ClienteModel execute(CreateClienteCommand command) {
        // Convertimos los tipos primitivos del DTO a Value Objects puros del Dominio
        ClienteEmail email = new ClienteEmail(command.correoElectronico());

        // Instanciamos el modelo
        ClienteModel nuevoCliente = ClienteModel.builder()
                .numeroIdentificacion(command.numeroIdentificacion())
                .nombre(command.nombre())
                .primerApellido(command.primerApellido())
                .segundoApellido(command.segundoApellido())
                .correoElectronico(email)
                .build();

        // Enviamos a guardar a través del puerto
        return repositoryPort.save(nuevoCliente);
    }
}