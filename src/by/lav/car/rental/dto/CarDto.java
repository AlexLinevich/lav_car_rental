package by.lav.car.rental.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CarDto {
    Integer id;
    String description;
    Integer carCategoryId;
}
