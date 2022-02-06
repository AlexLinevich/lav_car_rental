package by.lav.car.rental.service;

import by.lav.car.rental.dao.CarDao;
import by.lav.car.rental.dto.CarDto;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class CarService {

    private static final CarService INSTANCE = new CarService();

    private final CarDao carDao = CarDao.getInstance();

    private CarService() {
    }

    public List<CarDto> findAll() {
        return carDao.findAll().stream()
                .map(carEntity -> CarDto.builder()
                        .id(carEntity.getId())
                        .description(String.format("CAR MODEL: %s, COLOUR: %s, SEATS QUANTITY: %s.",
                                carEntity.getModel(), carEntity.getColour(), carEntity.getSeatsQuantity()))
                        .carCategoryId(carEntity.getCarCategory().getId())
                        .build())
                .collect(toList());
    }

    public static CarService getInstance() {
        return INSTANCE;
    }
}
