package com.jcaa.usersmanagement.application.service.dto.query;

import com.jcaa.usersmanagement.application.port.out.ClienteRepositoryPort;
import com.jcaa.usersmanagement.application.service.dto.query.FindAllClientesQuery;
import com.jcaa.usersmanagement.domain.model.ClienteModel;

import java.util.List;

public class FindAllClientesService {

    private final ClienteRepositoryPort repositoryPort;

    public FindAllClientesService(ClienteRepositoryPort repositoryPort) {
        this.repositoryPort = repositoryPort;
    }

    public List<ClienteModel> execute(FindAllClientesQuery query) {
        return repositoryPort.findAll();
    }
}