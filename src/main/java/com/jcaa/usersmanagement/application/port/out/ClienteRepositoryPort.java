package com.jcaa.usersmanagement.application.port.out;

import com.jcaa.usersmanagement.domain.model.ClienteModel;

import java.util.List;
import java.util.Optional;

public interface ClienteRepositoryPort {
    ClienteModel save(ClienteModel cliente);
    Optional<ClienteModel> findById(String id);
    List<ClienteModel> findAll();
    void delete(String id);
    ClienteModel update(ClienteModel cliente);
}