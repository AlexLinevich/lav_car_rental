package by.lav.car.rental.dao;

import by.lav.car.rental.entity.CarCategoryEntity;
import by.lav.car.rental.exception.DaoException;
import by.lav.car.rental.util.ConnectionManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class CarCategoryDao implements Dao<Integer, CarCategoryEntity> {

    private static final CarCategoryDao INSTANCE = new CarCategoryDao();
    private static final String DELETE_SQL =
            "DELETE FROM car_category " +
                    "WHERE id = ? ";
    private static final String SAVE_SQL =
            "INSERT INTO car_category (category, day_price) " +
                    "VALUES (?, ?) ";
    private static final String UPDATE_SQL =
            "UPDATE car_category " +
                    "SET category = ?, day_price = ? " +
                    "WHERE id = ? ";
    private static final String FIND_ALL_SQL =
            "SELECT id, category, day_price " +
                    "FROM car_category ";
    private static final String FIND_BY_ID_SQL =
            FIND_ALL_SQL +
                    "WHERE id = ? ";

    private CarCategoryDao() {
    }

    @Override
    public List<CarCategoryEntity> findAll() {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {
            var resultSet = preparedStatement.executeQuery();
            List<CarCategoryEntity> carCategoryEntities = new ArrayList<>();
            while (resultSet.next()) {
                carCategoryEntities.add(buildCarCategoryEntity(resultSet));
            }
            return carCategoryEntities;
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    @Override
    public Optional<CarCategoryEntity> findById(Integer id) {
        try (var connection = ConnectionManager.get()) {
            return findById(id, connection);
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    public Optional<CarCategoryEntity> findById(Integer id, Connection connection) {
        try (var preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            preparedStatement.setInt(1, id);

            var resultSet = preparedStatement.executeQuery();
            CarCategoryEntity carCategoryEntity = null;
            if (resultSet.next()) {
                carCategoryEntity = buildCarCategoryEntity(resultSet);
            }

            return Optional.ofNullable(carCategoryEntity);
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    private CarCategoryEntity buildCarCategoryEntity(ResultSet resultSet) throws SQLException {
        return new CarCategoryEntity(
                resultSet.getInt("id"),
                resultSet.getString("category"),
                resultSet.getBigDecimal("day_price")
        );
    }

    @Override
    public void update(CarCategoryEntity carCategoryEntity) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(UPDATE_SQL)) {
            preparedStatement.setString(1, carCategoryEntity.getCategory());
            preparedStatement.setBigDecimal(2, carCategoryEntity.getDayPrice());
            preparedStatement.setInt(3, carCategoryEntity.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    @Override
    public CarCategoryEntity save(CarCategoryEntity carCategoryEntity) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(SAVE_SQL, RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, carCategoryEntity.getCategory());
            preparedStatement.setBigDecimal(2, carCategoryEntity.getDayPrice());

            preparedStatement.executeUpdate();

            var generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                carCategoryEntity.setId(generatedKeys.getInt("id"));
            }
            return carCategoryEntity;
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    @Override
    public boolean delete(Integer id) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(DELETE_SQL)) {
            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    public static CarCategoryDao getInstance() {
        return INSTANCE;
    }
}
