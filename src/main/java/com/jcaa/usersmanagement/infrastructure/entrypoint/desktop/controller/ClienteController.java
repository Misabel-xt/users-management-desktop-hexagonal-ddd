package com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.controller;

import com.jcaa.usersmanagement.application.service.dto.command.*;
import com.jcaa.usersmanagement.application.service.dto.query.FindAllClientesQuery;
import com.jcaa.usersmanagement.application.service.dto.query.FindAllClientesService;
import com.jcaa.usersmanagement.application.service.dto.query.FindClienteByIdQuery;
import com.jcaa.usersmanagement.application.service.dto.query.FindClienteByIdService;
import com.jcaa.usersmanagement.domain.model.ClienteModel;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class ClienteController {

    private final CreateClienteService createClienteService;
    private final FindClienteByIdService findClienteByIdService;
    private final UpdateClienteService updateClienteService;
    private final DeleteClienteService deleteClienteService;
    private final FindAllClientesService findAllClientesService;

    public ClienteController(CreateClienteService createClienteService, FindClienteByIdService findClienteByIdService, UpdateClienteService updateClienteService, DeleteClienteService deleteClienteService, FindAllClientesService findAllClientesService) {
        this.createClienteService = createClienteService;
        this.findClienteByIdService = findClienteByIdService;
        this.updateClienteService = updateClienteService;
        this.deleteClienteService = deleteClienteService;
        this.findAllClientesService = findAllClientesService;
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

    public void actualizarCliente(Scanner scanner) {
        System.out.println("\n--- ACTUALIZAR CLIENTE ---");
        System.out.print("Ingrese el ID del cliente a actualizar: ");
        String id = scanner.nextLine();

        System.out.print("Nuevo Nombre: ");
        String nombre = scanner.nextLine();

        System.out.print("Nuevo Primer Apellido: ");
        String apellido1 = scanner.nextLine();

        System.out.print("Nuevo Segundo Apellido: ");
        String apellido2 = scanner.nextLine();

        System.out.print("Nuevo Correo Electrónico: ");
        String correo = scanner.nextLine();

        UpdateClienteCommand command = new UpdateClienteCommand(id, nombre, apellido1, apellido2, correo);

        try {
            updateClienteService.execute(command);
            System.out.println("Cliente actualizado exitosamente.");
        } catch (Exception e) {
            System.out.println("Error al actualizar: " + e.getMessage());
        }
    }

    public void eliminarCliente(Scanner scanner) {
        System.out.println("\n--- ELIMINAR CLIENTE ---");
        System.out.print("Ingrese el ID del cliente a eliminar: ");
        String id = scanner.nextLine();

        System.out.print("¿Está seguro que desea eliminar este cliente? (S/N): ");
        String confirmacion = scanner.nextLine();

        if (confirmacion.equalsIgnoreCase("S")) {
            try {
                deleteClienteService.execute(new DeleteClienteCommand(id));
                System.out.println("Cliente eliminado exitosamente.");
            } catch (Exception e) {
                System.out.println("Error al eliminar: " + e.getMessage());
            }
        } else {
            System.out.println("Operación cancelada.");
        }
    }

    public void listarClientes() {
        System.out.println("\n--- LISTA DE CLIENTES ---");
        try {
            List<ClienteModel> clientes = findAllClientesService.execute(new FindAllClientesQuery());

            if (clientes.isEmpty()) {
                System.out.println("No hay clientes registrados en la base de datos.");
            } else {
                for (ClienteModel c : clientes) {
                    System.out.println("ID: " + c.getNumeroIdentificacion() +
                            " | Nombre: " + c.getNombre() + " " + c.getPrimerApellido() +
                            " | Correo: " + c.getCorreoElectronico().value());
                }
            }
        } catch (Exception e) {
            System.out.println("Error al listar los clientes: " + e.getMessage());
        }
    }

}