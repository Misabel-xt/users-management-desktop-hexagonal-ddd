package com.jcaa.usersmanagement.application.service.dto.command;

import com.jcaa.usersmanagement.application.port.out.ClienteRepositoryPort;
import com.jcaa.usersmanagement.application.service.dto.command.DeleteClienteCommand;

public class DeleteClienteService {

    private final ClienteRepositoryPort repositoryPort;

    public DeleteClienteService(ClienteRepositoryPort repositoryPort) {
        this.repositoryPort = repositoryPort;
    }

    public void execute(DeleteClienteCommand command) {
        repositoryPort.findById(command.numeroIdentificacion())
                .orElseThrow(() -> new RuntimeException("El cliente con ID " + command.numeroIdentificacion() + " no existe o ya fue eliminado."));

        repositoryPort.delete(command.numeroIdentificacion());
    }
}