package by.lav.car.rental.dao;

import by.lav.car.rental.entity.ClientDataEntity;
import by.lav.car.rental.util.ConnectionManager;
import lombok.SneakyThrows;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class ClientDataDao implements Dao<Integer, ClientDataEntity> {

    private static final ClientDataDao INSTANCE = new ClientDataDao();
    private static final String DELETE_SQL =
            "DELETE FROM client_data " +
                    "WHERE id = ? ";
    private static final String SAVE_SQL =
            "INSERT INTO client_data(user_id, driver_licence_no, dl_expiration_day, credit_amount) " +
                    "VALUES (?, ?, ?, ?)";
    private static final String UPDATE_SQL =
            "UPDATE client_data " +
                    "SET user_id = ?, driver_licence_no = ?, dl_expiration_day = ?, credit_amount = ? " +
                    "WHERE id = ? ";
    private static final String FIND_ALL_SQL =
            "SELECT id, user_id, driver_licence_no, dl_expiration_day, credit_amount " +
                    "FROM client_data ";
    private static final String FIND_ALL_BY_ID_SQL =
            FIND_ALL_SQL +
                    " WHERE id = ? ";
    private static final String FIND_ID_BY_USER_ID_SQL =
            "SELECT id " +
                    "FROM client_data " +
                    "WHERE user_id = ? ";

    private ClientDataDao() {
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

    @Override
    @SneakyThrows
    public ClientDataEntity save(ClientDataEntity clientDataEntity) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(SAVE_SQL, RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, clientDataEntity.getUserId());
            preparedStatement.setString(2, clientDataEntity.getDriverLicenceNo());
            preparedStatement.setTimestamp(3,
                    Timestamp.valueOf(clientDataEntity.getDlExpirationDay().atStartOfDay()));
            preparedStatement.setBigDecimal(4, clientDataEntity.getCreditAmount());

            preparedStatement.executeUpdate();
            var generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                clientDataEntity.setId(generatedKeys.getInt("id"));
            }
            return clientDataEntity;
        }
    }

    @Override
    @SneakyThrows
    public void update(ClientDataEntity clientDataEntity) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(UPDATE_SQL)) {
            preparedStatement.setInt(1, clientDataEntity.getUserId());
            preparedStatement.setString(2, clientDataEntity.getDriverLicenceNo());
            preparedStatement.setTimestamp(3,
                    Timestamp.valueOf(clientDataEntity.getDlExpirationDay().atStartOfDay()));
            preparedStatement.setBigDecimal(4, clientDataEntity.getCreditAmount());
            preparedStatement.setInt(5, clientDataEntity.getId());

            preparedStatement.executeUpdate();
        }
    }

    @Override
    @SneakyThrows
    public Optional<ClientDataEntity> findById(Integer id) {
        try (var connection = ConnectionManager.get()) {
            return findById(id, connection);
        }
    }

    @SneakyThrows
    public Optional<ClientDataEntity> findById(Integer id, Connection connection) {
        try (var preparedStatement = connection.prepareStatement(FIND_ALL_BY_ID_SQL)) {
            preparedStatement.setInt(1, id);

            var resultSet = preparedStatement.executeQuery();
            ClientDataEntity clientDataEntity = null;
            if (resultSet.next()) {
                clientDataEntity = buildClientDataEntity(resultSet);
            }
            return Optional.ofNullable(clientDataEntity);
        }
    }

    @Override
    @SneakyThrows
    public List<ClientDataEntity> findAll() {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {
            var resultSet = preparedStatement.executeQuery();
            List<ClientDataEntity> clientDataEntities = new ArrayList<>();
            while (resultSet.next()) {
                clientDataEntities.add(buildClientDataEntity(resultSet));
            }
            return clientDataEntities;
        }
    }

    @SneakyThrows
    public Optional<Integer> findIdByUserId(Integer userId) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(FIND_ID_BY_USER_ID_SQL)) {
            preparedStatement.setInt(1, userId);

            var resultSet = preparedStatement.executeQuery();
            Integer id = null;
            if (resultSet.next()) {
                id = resultSet.getInt("id");
            }
            return Optional.ofNullable(id);
        }
    }

    @SneakyThrows
    private ClientDataEntity buildClientDataEntity(ResultSet resultSet) {
        return ClientDataEntity.builder()
                .id(resultSet.getObject("id", Integer.class))
                .userId(resultSet.getObject("user_id", Integer.class))
                .driverLicenceNo(resultSet.getObject("driver_licence_no", String.class))
                .dlExpirationDay(resultSet.getDate("dl_expiration_day").toLocalDate())
                .creditAmount(resultSet.getObject("credit_amount", BigDecimal.class))
                .build();
    }

    public static ClientDataDao getInstance() {
        return INSTANCE;
    }
}
