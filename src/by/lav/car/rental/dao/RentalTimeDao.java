package by.lav.car.rental.dao;


import by.lav.car.rental.entity.RentalTimeEntity;
import by.lav.car.rental.util.ConnectionManager;
import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class RentalTimeDao implements Dao<Integer, RentalTimeEntity> {

    private static final RentalTimeDao INSTANCE = new RentalTimeDao();
    private static final String DELETE_SQL =
            "DELETE FROM rental_time " +
                    "WHERE id = ? ";
    private static final String SAVE_SQL =
            "INSERT INTO rental_time(car_id, begin_time, end_time, order_id) " +
                    "VALUES (?, ?, ?, ?)";
    private static final String UPDATE_SQL =
            "UPDATE rental_time " +
                    "SET car_id = ?, begin_time = ?, end_time = ?, order_id = ? " +
                    "WHERE id = ? ";
    private static final String FIND_ALL_SQL =
            "SELECT id, car_id, begin_time, end_time, order_id " +
                    "FROM rental_time ";
    private static final String FIND_BY_ID_SQL =
            FIND_ALL_SQL +
                    " WHERE id = ? ";
    private static final String FIND_ALL_BY_CAR_ID_SQL =
            FIND_ALL_SQL +
                    " WHERE car_id = ? ";

    private RentalTimeDao() {
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
    public RentalTimeEntity save(RentalTimeEntity rentalTimeEntity) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(SAVE_SQL, RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, rentalTimeEntity.getCarId());
            preparedStatement.setTimestamp(2, Timestamp.valueOf(rentalTimeEntity.getBeginTime()));
            preparedStatement.setTimestamp(3, Timestamp.valueOf(rentalTimeEntity.getEndTime()));
            preparedStatement.setInt(4, rentalTimeEntity.getOrderId());

            preparedStatement.executeUpdate();
            var generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                rentalTimeEntity.setId(generatedKeys.getInt("id"));
            }
            return rentalTimeEntity;
        }
    }

    @Override
    @SneakyThrows
    public void update(RentalTimeEntity rentalTimeEntity) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(UPDATE_SQL)) {
            preparedStatement.setInt(1, rentalTimeEntity.getCarId());
            preparedStatement.setTimestamp(2, Timestamp.valueOf(rentalTimeEntity.getBeginTime()));
            preparedStatement.setTimestamp(3, Timestamp.valueOf(rentalTimeEntity.getEndTime()));
            preparedStatement.setInt(4, rentalTimeEntity.getOrderId());
            preparedStatement.setInt(5, rentalTimeEntity.getId());

            preparedStatement.executeUpdate();
        }
    }

    @Override
    @SneakyThrows
    public Optional<RentalTimeEntity> findById(Integer id) {
        try (var connection = ConnectionManager.get()) {
            return findById(id, connection);
        }
    }

    @SneakyThrows
    public Optional<RentalTimeEntity> findById(Integer id, Connection connection) {
        try (var preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            preparedStatement.setInt(1, id);

            var resultSet = preparedStatement.executeQuery();
            RentalTimeEntity rentalTimeEntity = null;
            if (resultSet.next()) {
                rentalTimeEntity = buildRentalTimeEntity(resultSet);
            }
            return Optional.ofNullable(rentalTimeEntity);
        }
    }

    @Override
    @SneakyThrows
    public List<RentalTimeEntity> findAll() {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {
            var resultSet = preparedStatement.executeQuery();
            List<RentalTimeEntity> rentalTimeEntities = new ArrayList<>();
            while (resultSet.next()) {
                rentalTimeEntities.add(buildRentalTimeEntity(resultSet));
            }
            return rentalTimeEntities;
        }
    }

    @SneakyThrows
    public List<RentalTimeEntity> findAllByCarId(Integer carId) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(FIND_ALL_BY_CAR_ID_SQL)) {
            preparedStatement.setInt(1, carId);
            var resultSet = preparedStatement.executeQuery();
            List<RentalTimeEntity> rentalTimeEntities = new ArrayList<>();
            while (resultSet.next()) {
                rentalTimeEntities.add(buildRentalTimeEntity(resultSet));
            }
            return rentalTimeEntities;
        }
    }

    @SneakyThrows
    private RentalTimeEntity buildRentalTimeEntity(ResultSet resultSet) {
        return RentalTimeEntity.builder()
                .id(resultSet.getObject("id", Integer.class))
                .carId(resultSet.getObject("car_id", Integer.class))
                .beginTime(resultSet.getTimestamp("begin_time").toLocalDateTime())
                .endTime(resultSet.getTimestamp("end_time").toLocalDateTime())
                .orderId(resultSet.getObject("order_id", Integer.class))
                .build();
    }

    public static RentalTimeDao getInstance() {
        return INSTANCE;
    }
}
