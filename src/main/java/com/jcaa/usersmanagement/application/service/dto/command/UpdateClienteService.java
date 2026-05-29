package com.jcaa.usersmanagement.application.service.dto.command;

import com.jcaa.usersmanagement.application.port.out.ClienteRepositoryPort;
import com.jcaa.usersmanagement.application.service.dto.command.UpdateClienteCommand;
import com.jcaa.usersmanagement.domain.model.ClienteModel;
import com.jcaa.usersmanagement.domain.valueobject.ClienteEmail;

public class UpdateClienteService {

    private final ClienteRepositoryPort repositoryPort;

    public UpdateClienteService(ClienteRepositoryPort repositoryPort) {
        this.repositoryPort = repositoryPort;
    }

    public ClienteModel execute(UpdateClienteCommand command) {
        // Verificamos que el cliente exista y extraemos sus datos actuales
        ClienteModel clienteExistente = repositoryPort.findById(command.numeroIdentificacion())
                .orElseThrow(() -> new RuntimeException("El cliente con ID " + command.numeroIdentificacion() + " no existe."));

        // Evaluamos qué datos se actualizarán (si el texto viene vacío, conservamos el dato anterior)
        String nuevoNombre = command.nombre().trim().isEmpty() ? clienteExistente.getNombre() : command.nombre();
        String nuevoPrimerApellido = command.primerApellido().trim().isEmpty() ? clienteExistente.getPrimerApellido() : command.primerApellido();
        String nuevoSegundoApellido = command.segundoApellido().trim().isEmpty() ? clienteExistente.getSegundoApellido() : command.segundoApellido();
        String nuevoCorreo = command.correoElectronico().trim().isEmpty() ? clienteExistente.getCorreoElectronico().value() : command.correoElectronico();

        // Ensamblamos el modelo actualizado
        ClienteModel clienteActualizado = ClienteModel.builder()
                .numeroIdentificacion(clienteExistente.getNumeroIdentificacion())
                .nombre(nuevoNombre)
                .primerApellido(nuevoPrimerApellido)
                .segundoApellido(nuevoSegundoApellido)
                .correoElectronico(new ClienteEmail(nuevoCorreo))
                .build();

        // Enviamos a la base de datos
        return repositoryPort.update(clienteActualizado);
    }
}