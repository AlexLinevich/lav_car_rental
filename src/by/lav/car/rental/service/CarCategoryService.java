package by.lav.car.rental.service;

import by.lav.car.rental.dao.CarCategoryDao;
import by.lav.car.rental.dto.CarCategoryDto;

public class CarCategoryService {

    private static final CarCategoryService INSTANCE = new CarCategoryService();

    private final CarCategoryDao carCategoryDao = CarCategoryDao.getInstance();

    private CarCategoryService() {
    }

    public CarCategoryDto findById(Integer id) {
        return carCategoryDao.findById(id)
                .map(carCategoryEntity -> CarCategoryDto
                        .builder()
                        .id(carCategoryEntity.getId())
                        .category(carCategoryEntity.getCategory())
                        .dayPrice(carCategoryEntity.getDayPrice())
                        .build())
                .orElseThrow();
    }

    public static CarCategoryService getInstance() {
        return INSTANCE;
    }
}
