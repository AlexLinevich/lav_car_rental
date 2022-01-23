package by.lav.car.rental.dao;

import by.lav.car.rental.entity.CarCategoryEntity;
import by.lav.car.rental.exception.DaoException;
import by.lav.car.rental.util.ConnectionManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class CarCategoryDao implements Dao<Integer, CarCategoryEntity> {

    public static final CarCategoryDao INSTANCE = new CarCategoryDao();
    private static final String FIND_BY_ID_SQL = """
            SELECT id,
                category,
                day_price
            FROM car_category
            WHERE id = ?
            """;

    private CarCategoryDao() {
    }

    public static CarCategoryDao getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }

    @Override
    public CarCategoryEntity save(CarCategoryEntity carEntity) {
        return null;
    }

    @Override
    public void update(CarCategoryEntity carEntity) {

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
                carCategoryEntity = new CarCategoryEntity(
                        resultSet.getInt("id"),
                        resultSet.getString("category"),
                        resultSet.getBigDecimal("day_price")
                );
            }
            return Optional.ofNullable(carCategoryEntity);
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    @Override
    public List<CarCategoryEntity> findAll() {
        return null;
    }
}
