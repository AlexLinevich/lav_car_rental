package by.lav.car.rental.service;

import by.lav.car.rental.dao.CarDao;
import by.lav.car.rental.dao.OrdersDao;
import by.lav.car.rental.dto.OrderDto;

import java.util.Optional;

public class SeeOrderService {

    private static final SeeOrderService INSTANCE = new SeeOrderService();

    private final OrdersDao ordersDao = OrdersDao.getInstance();
    private final CarDao carDao = CarDao.getInstance();

    private SeeOrderService() {
    }

    public Optional<OrderDto> findById(Integer id) {
        return ordersDao.findById(id)
                .map(ordersEntity -> OrderDto.builder()
                        .id(ordersEntity.getId())
                        .userId(ordersEntity.getUserId())
                        .carId(ordersEntity.getCarId())
                        .description(String.format("%s, BEGIN: %s, END: %s, STATUS: %s, MESSAGE: %s",
                                carDao.findById(ordersEntity.getCarId()).orElseThrow().getModel(),
                                ordersEntity.getBeginTime(),
                                ordersEntity.getEndTime(),
                                ordersEntity.getStatus().name(),
                                ordersEntity.getMessage()))
                        .beginTime(ordersEntity.getBeginTime())
                        .endTime(ordersEntity.getEndTime())
                        .status(ordersEntity.getStatus())
                        .message(ordersEntity.getMessage())
                        .build());
    }

    public static SeeOrderService getInstance() {
        return INSTANCE;
    }
}
