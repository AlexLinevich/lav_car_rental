package by.lav.car.rental.service;

import by.lav.car.rental.dao.CarCategoryDao;
import by.lav.car.rental.dto.CarCategoryDto;
import by.lav.car.rental.entity.CarCategoryEntity;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

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

    public List<String> findAllCategory() {
        return carCategoryDao.findAll()
                .stream()
                .map(CarCategoryEntity::getCategory)
                .collect(toList());
    }

    public Optional<CarCategoryEntity> findIdByCategory(String carCategory) {
        return carCategoryDao.findByCategory(carCategory);
    }

    public static CarCategoryService getInstance() {
        return INSTANCE;
    }
}
