package by.lav.car.rental.mapper;

import by.lav.car.rental.dto.CreateCarDto;
import by.lav.car.rental.entity.CarEntity;
import by.lav.car.rental.service.CarCategoryService;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class CreateCarMapper implements Mapper<CreateCarDto, CarEntity> {

    private static final String IMAGE_FOLDER = "cars\\";
    private static final CreateCarMapper INSTANCE = new CreateCarMapper();

    private final CarCategoryService carCategoryService = CarCategoryService.getInstance();

    @Override
    public CarEntity mapFrom(CreateCarDto object) {
        return new CarEntity(
                object.getModel(),
                carCategoryService.findIdByCategory(object.getCarCategory()).orElseThrow(),
                object.getColour(),
                Integer.valueOf(object.getSeatsQuantity()),
                IMAGE_FOLDER + object.getImage().getSubmittedFileName());
    }

    public static CreateCarMapper getInstance() {
        return INSTANCE;
    }
}
