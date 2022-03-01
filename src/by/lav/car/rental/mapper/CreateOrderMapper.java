package by.lav.car.rental.mapper;

import by.lav.car.rental.dto.CreateOrderDto;
import by.lav.car.rental.entity.OrdersEntity;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class CreateOrderMapper implements Mapper<CreateOrderDto, OrdersEntity> {

    private static final CreateOrderMapper INSTANCE = new CreateOrderMapper();

    @Override
    public OrdersEntity mapFrom(CreateOrderDto object) {
        return OrdersEntity.builder()
                .userId(object.getUserId())
                .carId(object.getCarId())
                .beginTime(LocalDateTime.parse(object.getBeginTime()))
                .endTime(LocalDateTime.parse(object.getEndTime()))
                .status(object.getOrderStatus())
                .message(object.getMessage())
                .build();
    }

    public static CreateOrderMapper getInstance() {
        return INSTANCE;
    }
}
