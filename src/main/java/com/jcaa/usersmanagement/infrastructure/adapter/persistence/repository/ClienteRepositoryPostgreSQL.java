package com.jcaa.usersmanagement.infrastructure.adapter.persistence.repository;

import com.jcaa.usersmanagement.application.port.out.ClienteRepositoryPort;
import com.jcaa.usersmanagement.domain.model.ClienteModel;
import com.jcaa.usersmanagement.infrastructure.adapter.persistence.entity.ClienteEntity;
import com.jcaa.usersmanagement.infrastructure.adapter.persistence.mapper.ClienteEntityMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Log
@RequiredArgsConstructor
public class ClienteRepositoryPostgreSQL implements ClienteRepositoryPort {

    private static final String SQL_INSERT =
            "INSERT INTO cliente (numero_identificacion, nombre, primer_apellido, segundo_apellido, correo_electronico) " +
                    "VALUES (?, ?, ?, ?, ?)";

    private static final String SQL_SELECT_BY_ID =
            "SELECT numero_identificacion, nombre, primer_apellido, segundo_apellido, correo_electronico " +
                    "FROM cliente WHERE numero_identificacion = ? LIMIT 1";

    private static final String SQL_SELECT_ALL =
            "SELECT numero_identificacion, nombre, primer_apellido, segundo_apellido, correo_electronico FROM cliente";

    private static final String SQL_DELETE =
            "DELETE FROM cliente WHERE numero_identificacion = ?";

    private static final String SQL_UPDATE =
            "UPDATE cliente SET nombre = ?, primer_apellido = ?, segundo_apellido = ?, correo_electronico = ? WHERE numero_identificacion = ?";

    private final Connection connection;
    private final ClienteEntityMapper mapper = new ClienteEntityMapper();

    @Override
    public ClienteModel save(ClienteModel cliente) {
        ClienteEntity entity = mapper.toEntity(cliente);
        try (PreparedStatement statement = connection.prepareStatement(SQL_INSERT)) {
            // Conversión de String a Int para la base de datos
            statement.setInt(1, Integer.parseInt(entity.getNumero_identificacion()));
            statement.setString(2, entity.getNombre());
            statement.setString(3, entity.getPrimer_apellido());
            statement.setString(4, entity.getSegundo_apellido());
            statement.setString(5, entity.getCorreo_electronico());
            statement.executeUpdate();
            return cliente;
        } catch (SQLException exception) {
            throw new RuntimeException("Error al guardar el cliente en la base de datos", exception);
        } catch (NumberFormatException exception) {
            throw new RuntimeException("El número de identificación debe ser numérico y no exceder la capacidad de un Integer", exception);
        }
    }

    @Override
    public Optional<ClienteModel> findById(String id) {
        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BY_ID)) {
            // Conversión de String a Int para la base de datos
            statement.setInt(1, Integer.parseInt(id));
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                return Optional.empty();
            }
            ClienteEntity entity = ClienteEntity.builder()
                    // Extracción como Int y conversión a String para la entidad
                    .numero_identificacion(String.valueOf(resultSet.getInt("numero_identificacion")))
                    .nombre(resultSet.getString("nombre"))
                    .primer_apellido(resultSet.getString("primer_apellido"))
                    .segundo_apellido(resultSet.getString("segundo_apellido"))
                    .correo_electronico(resultSet.getString("correo_electronico"))
                    .build();
            return Optional.of(mapper.toModel(entity));
        } catch (SQLException exception) {
            throw new RuntimeException("Error al buscar el cliente", exception);
        } catch (NumberFormatException exception) {
            throw new RuntimeException("El formato del ID buscado no es válido para entero", exception);
        }
    }

    @Override
    public List<ClienteModel> findAll() {
        List<ClienteModel> clientes = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                ClienteEntity entity = ClienteEntity.builder()
                        // Extracción como Int y conversión a String para la entidad
                        .numero_identificacion(String.valueOf(resultSet.getInt("numero_identificacion")))
                        .nombre(resultSet.getString("nombre"))
                        .primer_apellido(resultSet.getString("primer_apellido"))
                        .segundo_apellido(resultSet.getString("segundo_apellido"))
                        .correo_electronico(resultSet.getString("correo_electronico"))
                        .build();
                clientes.add(mapper.toModel(entity));
            }
            return clientes;
        } catch (SQLException exception) {
            throw new RuntimeException("Error al listar los clientes", exception);
        }
    }

    @Override
    public ClienteModel update(ClienteModel cliente) {
        ClienteEntity entity = mapper.toEntity(cliente);
        try (PreparedStatement statement = connection.prepareStatement(SQL_UPDATE)) {
            statement.setString(1, entity.getNombre());
            statement.setString(2, entity.getPrimer_apellido());
            statement.setString(3, entity.getSegundo_apellido());
            statement.setString(4, entity.getCorreo_electronico());
            statement.setInt(5, Integer.parseInt(entity.getNumero_identificacion())); // El ID va al final para el WHERE
            statement.executeUpdate();
            return cliente;
        } catch (SQLException exception) {
            throw new RuntimeException("Error al actualizar el cliente en la base de datos", exception);
        } catch (NumberFormatException exception) {
            throw new RuntimeException("El número de identificación debe ser numérico", exception);
        }
    }

    @Override
    public void delete(String id) {
        try (PreparedStatement statement = connection.prepareStatement(SQL_DELETE)) {
            statement.setInt(1, Integer.parseInt(id));
            statement.executeUpdate();
        } catch (SQLException exception) {
            throw new RuntimeException("Error al eliminar el cliente", exception);
        } catch (NumberFormatException exception) {
            throw new RuntimeException("El formato del ID a eliminar no es válido para entero", exception);
        }
    }
}