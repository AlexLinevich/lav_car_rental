package by.lav.car.rental.dao;

import by.lav.car.rental.entity.OrderStatus;
import by.lav.car.rental.entity.OrdersEntity;
import by.lav.car.rental.util.ConnectionManager;
import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrdersDao implements Dao<Integer, OrdersEntity> {

    private static final OrdersDao INSTANCE = new OrdersDao();
    private static final String DELETE_SQL =
            "DELETE FROM orders " +
                    "WHERE id = ? ";
    private static final String SAVE_SQL =
            "INSERT INTO orders(user_id, car_id, begin_time, end_time, status, message) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_SQL =
            "UPDATE orders " +
                    "SET user_id = ?, car_id = ?, begin_time = ?, end_time = ?, status = ?, message = ? " +
                    "WHERE id = ? ";
    private static final String FIND_ALL_SQL =
            "SELECT id, user_id, car_id, begin_time, end_time, status, message " +
                    "FROM orders ";
    private static final String FIND_ALL_BY_ID_SQL =
            FIND_ALL_SQL +
                    " WHERE id = ? ";

    private OrdersDao() {
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
    public OrdersEntity save(OrdersEntity ordersEntity) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(SAVE_SQL)) {
            preparedStatement.setInt(1, ordersEntity.getUserId());
            preparedStatement.setInt(2, ordersEntity.getCarId());
            preparedStatement.setTimestamp(3,
                    Timestamp.valueOf(String.valueOf(ordersEntity.getBeginTime())));
            preparedStatement.setTimestamp(4,
                    Timestamp.valueOf(String.valueOf(ordersEntity.getEndTime())));
            preparedStatement.setString(5, ordersEntity.getStatus().name());
            preparedStatement.setString(6, ordersEntity.getMessage());

            preparedStatement.executeUpdate();
            var generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                ordersEntity.setId(generatedKeys.getInt("id"));
            }
            return ordersEntity;
        }
    }

    @Override
    @SneakyThrows
    public void update(OrdersEntity ordersEntity) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(UPDATE_SQL)) {
            preparedStatement.setInt(1, ordersEntity.getUserId());
            preparedStatement.setInt(2, ordersEntity.getCarId());
            preparedStatement.setTimestamp(3,
                    Timestamp.valueOf(String.valueOf(ordersEntity.getBeginTime())));
            preparedStatement.setTimestamp(4,
                    Timestamp.valueOf(String.valueOf(ordersEntity.getEndTime())));
            preparedStatement.setString(5, ordersEntity.getStatus().name());
            preparedStatement.setString(6, ordersEntity.getMessage());
            preparedStatement.setInt(7, ordersEntity.getId());

            preparedStatement.executeUpdate();
        }
    }

    @Override
    @SneakyThrows
    public Optional<OrdersEntity> findById(Integer id) {
        try (var connection = ConnectionManager.get()) {
            return findById(id, connection);
        }
    }

    @SneakyThrows
    private Optional<OrdersEntity> findById(Integer id, Connection connection) {
        try (var preparedStatement = connection.prepareStatement(FIND_ALL_BY_ID_SQL)) {
            preparedStatement.setInt(1, id);

            var resultSet = preparedStatement.executeQuery();
            OrdersEntity ordersEntity = null;
            if (resultSet.next()) {
                ordersEntity = buildOrdersEntity(resultSet);
            }
            return Optional.ofNullable(ordersEntity);
        }
    }

    @Override
    @SneakyThrows
    public List<OrdersEntity> findAll() {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {
            var resultSet = preparedStatement.executeQuery();
            List<OrdersEntity> ordersEntities = new ArrayList<>();
            while (resultSet.next()) {
                ordersEntities.add(buildOrdersEntity(resultSet));
            }
            return ordersEntities;
        }
    }

    @SneakyThrows
    private OrdersEntity buildOrdersEntity(ResultSet resultSet) {
        return OrdersEntity.builder()
                .id(resultSet.getObject("id", Integer.class))
                .userId(resultSet.getObject("user_id", Integer.class))
                .carId(resultSet.getObject("car_id", Integer.class))
                .beginTime(resultSet.getTimestamp("begin_time").toLocalDateTime())
                .endTime(resultSet.getTimestamp("end_time").toLocalDateTime())
                .status(OrderStatus.valueOf(resultSet.getObject("status", String.class)))
                .message(resultSet.getString("message"))
                .build();
    }

    public static OrdersDao getInstance() {
        return INSTANCE;
    }
}
