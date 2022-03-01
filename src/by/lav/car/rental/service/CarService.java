package by.lav.car.rental.service;

import by.lav.car.rental.dao.CarDao;
import by.lav.car.rental.dto.CarDto;
import by.lav.car.rental.dto.CreateCarDto;
import by.lav.car.rental.mapper.CreateCarMapper;
import lombok.SneakyThrows;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class CarService {

    private static final CarService INSTANCE = new CarService();

    private final CarDao carDao = CarDao.getInstance();
    private final CreateCarMapper createCarMapper = CreateCarMapper.getInstance();
    private final ImageService imageService = ImageService.getInstance();

    private CarService() {
    }

    public List<CarDto> findAll() {
        return carDao.findAll().stream()
                .map(carEntity -> CarDto.builder()
                        .id(carEntity.getId())
                        .description(String.format("%s, %s, %s, SEATS# %s, %s $ per day.",
                                carEntity.getModel(), carEntity.getColour(), carEntity.getCarCategory().getCategory(),
                                carEntity.getSeatsQuantity(), carEntity.getCarCategory().getDayPrice()))
                        .carCategoryId(carEntity.getCarCategory().getId())
                        .image(carEntity.getImage())
                        .build())
                .collect(toList());
    }

    public Integer findByDescription(List<CarDto> cars, String description) {
        return cars.stream()
                .filter(carDto -> carDto.getDescription().equals(description))
                .mapToInt(CarDto::getId)
                .sum();
    }

    @SneakyThrows
    public Integer create(CreateCarDto createCarDto) {
        var carEntity = createCarMapper.mapFrom(createCarDto);
        imageService.upload(carEntity.getImage(), createCarDto.getImage().getInputStream());
        carDao.save(carEntity);
        return carEntity.getId();
    }

    public static CarService getInstance() {
        return INSTANCE;
    }
}
