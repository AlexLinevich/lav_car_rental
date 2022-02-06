package by.lav.car.rental.dao;

import by.lav.car.rental.entity.CarEntity;
import by.lav.car.rental.exception.DaoException;
import by.lav.car.rental.util.ConnectionManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CarDao implements Dao<Integer, CarEntity> {

    private static final CarDao INSTANCE = new CarDao();
    private static final String DELETE_SQL =
            "DELETE FROM car " +
                    "WHERE id = ? ";
    private static final String SAVE_SQL =
            "INSERT INTO car(model, car_category_id, colour, seats_quantity) " +
                    "VALUES (?, ?, ?, ?) ";
    private static final String UPDATE_SQL =
            "UPDATE car " +
                    "SET model = ?, car_category_id = ?, colour = ?, seats_quantity = ? " +
                    "WHERE id = ? ";
    private static final String FIND_ALL_SQL =
            "SELECT id, model, car_category_id, colour, seats_quantity " +
                    "FROM car ";
    private static final String FIND_BY_ID_SQL =
            FIND_ALL_SQL +
                    " WHERE id = ? ";
    private final CarCategoryDao carCategoryDao = CarCategoryDao.getInstance();

    private CarDao() {
    }

    @Override
    public List<CarEntity> findAll() {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {
            var resultSet = preparedStatement.executeQuery();
            List<CarEntity> carEntities = new ArrayList<>();
            while (resultSet.next()) {
                carEntities.add(buildCarEntity(resultSet));
            }
            return carEntities;
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    @Override
    public Optional<CarEntity> findById(Integer id) {
        try (var connection = ConnectionManager.get()) {
            return findById(id, connection);
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    private Optional<CarEntity> findById(Integer id, Connection connection) {
        try (var preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            preparedStatement.setInt(1, id);

            var resultSet = preparedStatement.executeQuery();
            CarEntity carEntity = null;
            if (resultSet.next()) {
                carEntity = buildCarEntity(resultSet);
            }

            return Optional.ofNullable(carEntity);
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    private CarEntity buildCarEntity(ResultSet resultSet) throws SQLException {
        return new CarEntity(
                resultSet.getInt("id"),
                resultSet.getString("model"),
                carCategoryDao.findById(resultSet.getInt("car_category_id"),
                        resultSet.getStatement().getConnection()).orElse(null),
                resultSet.getString("colour"),
                resultSet.getInt("seats_quantity")
        );
    }

    @Override
    public void update(CarEntity carEntity) {
        try (Connection connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(UPDATE_SQL)) {
            preparedStatement.setString(1, carEntity.getModel());
            preparedStatement.setInt(2, carEntity.getCarCategory().getId());
            preparedStatement.setString(3, carEntity.getColour());
            preparedStatement.setInt(4, carEntity.getSeatsQuantity());
            preparedStatement.setInt(5, carEntity.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    @Override
    public CarEntity save(CarEntity carEntity) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, carEntity.getModel());
            preparedStatement.setInt(2, carEntity.getCarCategory().getId());
            preparedStatement.setString(3, carEntity.getColour());
            preparedStatement.setInt(4, carEntity.getSeatsQuantity());

            preparedStatement.executeUpdate();

            var generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                carEntity.setId(generatedKeys.getInt("id"));
            }
            return carEntity;
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

    public static CarDao getInstance() {
        return INSTANCE;
    }
}
