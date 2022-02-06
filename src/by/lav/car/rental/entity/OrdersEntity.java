package by.lav.car.rental.entity;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class OrdersEntity {

    private Integer id;
    private Integer userId;
    private Integer carId;
    private LocalDateTime beginTime;
    private LocalDateTime endTime;
    private OrderStatus status;
    private String message;
}
