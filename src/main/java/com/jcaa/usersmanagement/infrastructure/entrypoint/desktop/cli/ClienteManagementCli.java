package com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli;

import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.handler.CreateClienteHandler;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.handler.OperationHandler;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.io.ConsoleIO;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.menu.ClienteMenuOption;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.controller.ClienteController;
import jakarta.validation.ConstraintViolationException;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class ClienteManagementCli {

    private static final String BANNER =
            """
            ==========================================
                 Gestión de Clientes
            ==========================================""";

    private static final String MENU_BORDER = "  ==========================================";

    private final ClienteController clienteController;
    private final ConsoleIO console;

    public void start() {
        console.println(BANNER);
        runLoop(buildHandlers());
    }

    private void runLoop(final Map<ClienteMenuOption, OperationHandler> handlers) {
        boolean running = true;
        while (running) {
            printMenu();
            final int choice = console.readInt("\n  Opción: ");
            final Optional<ClienteMenuOption> option = ClienteMenuOption.fromNumber(choice);

            if (option.isEmpty()) {
                console.println("  Opción inválida. Intente de nuevo.");
            } else if (option.get() == ClienteMenuOption.EXIT) {
                console.println("\n  ¡Hasta luego!\n");
                running = false;
            } else {
                executeHandler(handlers, option.get());
            }
        }
    }

    private void executeHandler(
            final Map<ClienteMenuOption, OperationHandler> handlers, final ClienteMenuOption option) {
        try {
            handlers.get(option).handle();
        } catch (final ConstraintViolationException exception) {
            console.println("  Errores de validación:");
            exception.getConstraintViolations()
                    .forEach(violation -> console.println("    - " + violation.getMessage()));
        } catch (final RuntimeException exception) {
            console.println("  Error inesperado: " + exception.getMessage());
        }
    }

    private Map<ClienteMenuOption, OperationHandler> buildHandlers() {
        return Map.of(
                ClienteMenuOption.CREATE_CLIENTE, new CreateClienteHandler(clienteController, console)
        );
    }

    private void printMenu() {
        console.println();
        console.println(MENU_BORDER);
        console.println("    Menú Principal de Clientes");
        console.println(MENU_BORDER);
        for (final ClienteMenuOption option : ClienteMenuOption.values()) {
            console.printf("    [%d] %s%n", option.getNumber(), option.getDescription());
        }
        console.println(MENU_BORDER);
    }
}