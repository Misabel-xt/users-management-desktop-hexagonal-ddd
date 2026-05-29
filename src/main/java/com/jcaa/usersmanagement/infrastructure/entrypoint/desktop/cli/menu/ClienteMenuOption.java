package com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.menu;

import java.util.Arrays;
import java.util.Optional;

public enum ClienteMenuOption {
    CREATE_CLIENTE(1, "Crear Cliente"),
    FIND_CLIENTE(2, "Buscar Cliente por ID"),
    UPDATE_CLIENTE(3, "Actualizar Cliente"),
    EXIT(0, "Salir");

    private final int number;
    private final String description;

    ClienteMenuOption(int number, String description) {
        this.number = number;
        this.description = description;
    }

    public int getNumber() { return number; }
    public String getDescription() { return description; }

    public static Optional<ClienteMenuOption> fromNumber(int number) {
        return Arrays.stream(values())
                .filter(option -> option.number == number)
                .findFirst();
    }
}