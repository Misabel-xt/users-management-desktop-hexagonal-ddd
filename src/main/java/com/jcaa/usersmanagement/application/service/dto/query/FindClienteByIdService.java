package com.jcaa.usersmanagement.application.service.dto.query;

import com.jcaa.usersmanagement.application.port.out.ClienteRepositoryPort;
import com.jcaa.usersmanagement.application.service.dto.query.FindClienteByIdQuery;
import com.jcaa.usersmanagement.domain.model.ClienteModel;

import java.util.Optional;

public class FindClienteByIdService {

    private final ClienteRepositoryPort repositoryPort;

    public FindClienteByIdService(ClienteRepositoryPort repositoryPort) {
        this.repositoryPort = repositoryPort;
    }

    public Optional<ClienteModel> execute(FindClienteByIdQuery query) {
        return repositoryPort.findById(query.numeroIdentificacion());
    }
}