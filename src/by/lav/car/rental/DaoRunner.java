package by.lav.car.rental;

import by.lav.car.rental.dao.CarDao;
import by.lav.car.rental.entity.CarCategoryEntity;
import by.lav.car.rental.entity.CarEntity;
import by.lav.car.rental.util.ConnectionManager;

public class DaoRunner {

    public static void main(String[] args) {
        try {
            findByIdTest();
//        testFindAll();
//        updateTest();
//        deleteTest();
//        saveTest();
        } finally {
            ConnectionManager.closePool();
        }

    }

    private static void findByIdTest() {
        var carEntities = CarDao.getInstance().findById(11);
        System.out.println(carEntities);
    }

    private static void findAllTest() {
        var carEntities = CarDao.getInstance().findAll();
        System.out.println(carEntities);
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
        var carCategory = new CarCategoryEntity();
        var carEntity = new CarEntity();
        carEntity.setModel("TOYOTA RAV4");
        carEntity.setCarCategory(carCategory);
        carEntity.setColour("white");
        carEntity.setSeatsQuantity(5);

        var savedCarEntity = carDao.save(carEntity);
        System.out.println(savedCarEntity);
    }
}
