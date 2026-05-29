package com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.handler;

import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.io.ConsoleIO;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.controller.ClienteController;
import lombok.RequiredArgsConstructor;
import java.util.Scanner;

@RequiredArgsConstructor
public class CreateClienteHandler implements OperationHandler {

    private final ClienteController clienteController;
    private final ConsoleIO console;

    @Override
    public void handle() {
        Scanner scanner = new Scanner(System.in);
        clienteController.crearCliente(scanner);
    }
}