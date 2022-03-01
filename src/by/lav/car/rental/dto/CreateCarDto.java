package by.lav.car.rental.dto;

import jakarta.servlet.http.Part;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CreateCarDto {
    String model;
    String carCategory;
    String colour;
    String seatsQuantity;
    Part image;
}
