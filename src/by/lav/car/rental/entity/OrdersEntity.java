package by.lav.car.rental.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
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
