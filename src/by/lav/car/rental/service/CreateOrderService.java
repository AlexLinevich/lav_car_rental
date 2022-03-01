package by.lav.car.rental.service;

import by.lav.car.rental.dao.OrdersDao;
import by.lav.car.rental.dto.CreateOrderDto;
import by.lav.car.rental.mapper.CreateOrderMapper;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class CreateOrderService {

    private static final CreateOrderService INSTANCE = new CreateOrderService();

    private final OrdersDao ordersDao = OrdersDao.getInstance();
    private final CreateOrderMapper createOrderMapper = CreateOrderMapper.getInstance();

    public void create(CreateOrderDto createOrderDto) {
        var ordersEntity = createOrderMapper.mapFrom(createOrderDto);
        ordersDao.save(ordersEntity);
    }

    public static CreateOrderService getInstance() {
        return INSTANCE;
    }
}
