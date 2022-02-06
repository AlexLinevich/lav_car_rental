package by.lav.car.rental.entity;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class RentalTimeEntity {

    private Integer id;
    private Integer carId;
    private LocalDateTime beginTime;
    private LocalDateTime endTime;
    private Integer orderId;
}
