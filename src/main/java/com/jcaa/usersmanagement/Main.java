package com.jcaa.usersmanagement;

import com.jcaa.usersmanagement.infrastructure.config.DependencyContainer;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.ClienteManagementCli;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.io.ConsoleIO;
import java.util.Scanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Main {

  private static final Logger log = LoggerFactory.getLogger(Main.class);

  public static void main(final String[] args) {
    log.info("Starting Clientes Management System...");
    final DependencyContainer container = new DependencyContainer();

    try (final Scanner scanner = new Scanner(System.in)) {
      new ClienteManagementCli(container.clienteController(), new ConsoleIO(scanner, System.out)).start();
    }
  }
}