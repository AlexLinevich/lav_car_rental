package by.lav.car.rental.dto;

import by.lav.car.rental.entity.OrderStatus;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CreateOrderDto {
    Integer userId;
    Integer carId;
    String beginTime;
    String endTime;
    OrderStatus orderStatus;
    String message;
}
