package com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.controller;

import com.jcaa.usersmanagement.application.service.dto.command.CreateClienteService;
import com.jcaa.usersmanagement.application.service.dto.command.CreateClienteCommand;

import java.util.Scanner;

public class ClienteController {

    private final CreateClienteService createClienteService;

    public ClienteController(CreateClienteService createClienteService) {
        this.createClienteService = createClienteService;
    }

    public void crearCliente(Scanner scanner) {
        System.out.println("\n--- CREAR NUEVO CLIENTE ---");
        System.out.print("Número de Identificación: ");
        String id = scanner.nextLine();

        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();

        System.out.print("Primer Apellido: ");
        String apellido1 = scanner.nextLine();

        System.out.print("Segundo Apellido: ");
        String apellido2 = scanner.nextLine();

        System.out.print("Correo Electrónico: ");
        String correo = scanner.nextLine();

        CreateClienteCommand command = new CreateClienteCommand(id, nombre, apellido1, apellido2, correo);

        try {
            createClienteService.execute(command);
            System.out.println("Cliente guardado exitosamente.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            // Esta validación extrae y muestra el error original de la base de datos
            if (e.getCause() != null) {
                System.out.println("Detalle técnico: " + e.getCause().getMessage());
            }
        }
    }
}