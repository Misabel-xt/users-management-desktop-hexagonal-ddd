package com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.controller;

import com.jcaa.usersmanagement.application.service.dto.command.CreateClienteService;
import com.jcaa.usersmanagement.application.service.dto.command.CreateClienteCommand;
import com.jcaa.usersmanagement.application.service.dto.query.FindClienteByIdQuery;
import com.jcaa.usersmanagement.application.service.dto.query.FindClienteByIdService;
import com.jcaa.usersmanagement.domain.model.ClienteModel;

import java.util.Optional;
import java.util.Scanner;

public class ClienteController {

    private final CreateClienteService createClienteService;
    private final FindClienteByIdService findClienteByIdService;

    public ClienteController(CreateClienteService createClienteService, FindClienteByIdService findClienteByIdService) {
        this.createClienteService = createClienteService;
        this.findClienteByIdService = findClienteByIdService;
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

    public void buscarCliente(Scanner scanner) {
        System.out.println("\n--- BUSCAR CLIENTE POR ID ---");
        System.out.print("Ingrese el Número de Identificación: ");
        String id = scanner.nextLine();

        FindClienteByIdQuery query = new FindClienteByIdQuery(id);

        try {
            Optional<ClienteModel> cliente = findClienteByIdService.execute(query);

            if (cliente.isPresent()) {
                ClienteModel c = cliente.get();
                System.out.println("\n Cliente encontrado:");
                System.out.println("ID: " + c.getNumeroIdentificacion());
                System.out.println("Nombre: " + c.getNombre() + " " + c.getPrimerApellido() + " " + c.getSegundoApellido());
                System.out.println("Correo: " + c.getCorreoElectronico().value());
            } else {
                System.out.println("No se encontró ningún cliente con ese ID.");
            }
        } catch (Exception e) {
            System.out.println("Error al buscar: " + e.getMessage());
        }
    }
}