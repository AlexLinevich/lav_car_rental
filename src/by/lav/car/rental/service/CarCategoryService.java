package by.lav.car.rental.service;

import by.lav.car.rental.dao.CarCategoryDao;
import by.lav.car.rental.dto.CarCategoryDto;

import java.util.Optional;

public class CarCategoryService {

    private static final CarCategoryService INSTANCE = new CarCategoryService();

    private final CarCategoryDao carCategoryDao = CarCategoryDao.getInstance();

    private CarCategoryService() {
    }

    public Optional<CarCategoryDto> findById(Integer id) {
        return carCategoryDao.findById(id)
                .map(carCategoryEntity -> CarCategoryDto.builder()
                        .id(carCategoryEntity.getId())
                        .category(carCategoryEntity.getCategory())
                        .dayPrice(carCategoryEntity.getDayPrice())
                        .build());
    }

    public static CarCategoryService getInstance() {
        return INSTANCE;
    }
}
