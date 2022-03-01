package by.lav.car.rental.dto;

import by.lav.car.rental.entity.OrderStatus;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder
public class OrderDto {
    Integer id;
    Integer userId;
    Integer carId;
    String description;
    LocalDateTime beginTime;
    LocalDateTime endTime;
    OrderStatus status;
    String message;
}
