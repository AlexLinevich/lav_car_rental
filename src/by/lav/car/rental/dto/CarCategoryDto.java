package by.lav.car.rental.dto;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

@Value
@Builder
public class CarCategoryDto {

    Integer id;
    String category;
    BigDecimal dayPrice;
}
