package com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.handler;

import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.controller.ClienteController;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ListClientesHandler implements OperationHandler {

    private final ClienteController clienteController;

    @Override
    public void handle() {
        clienteController.listarClientes();
    }
}