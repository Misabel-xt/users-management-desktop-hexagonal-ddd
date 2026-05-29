package com.jcaa.usersmanagement.application.service.dto.command;

public record UpdateClienteCommand(String numeroIdentificacion, String nombre, String primerApellido, String segundoApellido, String correoElectronico) {
}