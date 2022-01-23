package by.lav.car.rental;

import by.lav.car.rental.dao.CarDao;
import by.lav.car.rental.entity.CarEntity;

public class DaoRunner {

    public static void main(String[] args) {
        final var carEntities = CarDao.getInstance().findAll();
        System.out.println(carEntities);
//        updateTest();
//        deleteTest();
//        saveTest();
    }

    private static void updateTest() {
        var carDao = CarDao.getInstance();
        var maybeCarEntity = carDao.findById(9);
        System.out.println(maybeCarEntity);

        maybeCarEntity.ifPresent(carEntity -> {
            carEntity.setSeatsQuantity(6);
            carDao.update(carEntity);
        });
    }

    private static void deleteTest() {
        var carDao = CarDao.getInstance();
        var deleteResult = carDao.delete(10);
        System.out.println(deleteResult);
    }

    private static void saveTest() {
        var carDao = CarDao.getInstance();
        var carEntity = new CarEntity();
        carEntity.setModel("TOYOTA RAV4");
        carEntity.setCategory("MIDDLE SUV");
        carEntity.setColour("white");
        carEntity.setSeatsQuantity(5);

        var savedCarEntity = carDao.save(carEntity);
        System.out.println(savedCarEntity);
    }
}
