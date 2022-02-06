package by.lav.car.rental.dao;

import by.lav.car.rental.entity.UsersEntity;
import by.lav.car.rental.entity.UsersRole;
import by.lav.car.rental.util.ConnectionManager;
import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class UsersDao implements Dao<Integer, UsersEntity> {

    private static final UsersDao INSTANCE = new UsersDao();
    private static final String DELETE_SQL =
            "DELETE FROM users " +
                    "WHERE id = ? ";
    private static final String SAVE_SQL =
            "INSERT INTO users(first_name, last_name, email, password, role) " +
                    "VALUES (?, ?, ?, ?, ?)";
    private static final String UPDATE_SQL =
            "UPDATE users " +
                    "SET first_name = ?, last_name = ?, email = ?, password = ?, role = ? " +
                    "WHERE id = ? ";
    private static final String FIND_ALL_SQL =
            "SELECT id, first_name, last_name, email, password, role " +
                    "FROM users ";
    private static final String FIND_ALL_BY_ID_SQL =
            FIND_ALL_SQL +
                    " WHERE id = ? ";

    private UsersDao() {
    }

    @Override
    @SneakyThrows
    public List<UsersEntity> findAll() {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {
            var resultSet = preparedStatement.executeQuery();
            List<UsersEntity> usersEntities = new ArrayList<>();
            while (resultSet.next()) {
                usersEntities.add(buildUsersEntity(resultSet));
            }
            return usersEntities;
        }
    }

    @Override
    @SneakyThrows
    public Optional<UsersEntity> findById(Integer id) {
        try (var connection = ConnectionManager.get()) {
            return findById(id, connection);
        }
    }

    @SneakyThrows
    private Optional<UsersEntity> findById(Integer id, Connection connection) {
        try (var preparedStatement = connection.prepareStatement(FIND_ALL_BY_ID_SQL)) {
            preparedStatement.setInt(1, id);

            var resultSet = preparedStatement.executeQuery();
            UsersEntity usersEntity = null;
            if (resultSet.next()) {
                usersEntity = buildUsersEntity(resultSet);
            }
            return Optional.ofNullable(usersEntity);
        }
    }

    @SneakyThrows
    private UsersEntity buildUsersEntity(ResultSet resultSet) {
        return UsersEntity.builder()
                .id(resultSet.getObject("id", Integer.class))
                .firstName(resultSet.getObject("first_name", String.class))
                .lastName(resultSet.getObject("last_name", String.class))
                .email(resultSet.getObject("email", String.class))
                .password(resultSet.getObject("password", String.class))
                .role(UsersRole.valueOf(resultSet.getObject("role", String.class)))
                .build();
    }

    @Override
    @SneakyThrows
    public void update(UsersEntity usersEntity) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(UPDATE_SQL)) {
            preparedStatement.setString(1, usersEntity.getFirstName());
            preparedStatement.setString(2, usersEntity.getLastName());
            preparedStatement.setString(3, usersEntity.getEmail());
            preparedStatement.setString(4, usersEntity.getPassword());
            preparedStatement.setString(5, usersEntity.getRole().name());
            preparedStatement.setInt(6, usersEntity.getId());

            preparedStatement.executeUpdate();
        }
    }

    @Override
    @SneakyThrows
    public UsersEntity save(UsersEntity usersEntity) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(SAVE_SQL)) {
            preparedStatement.setString(1, usersEntity.getFirstName());
            preparedStatement.setString(2, usersEntity.getLastName());
            preparedStatement.setString(3, usersEntity.getEmail());
            preparedStatement.setString(4, usersEntity.getPassword());
            preparedStatement.setString(5, usersEntity.getRole().name());

            preparedStatement.executeUpdate();
            var generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                usersEntity.setId(generatedKeys.getInt("id"));
            }
            return usersEntity;
        }
    }

    @Override
    @SneakyThrows
    public boolean delete(Integer id) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(DELETE_SQL)) {
            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate() > 0;
        }
    }

    public static UsersDao getInstance() {
        return INSTANCE;
    }
}
