package by.lav.car.rental.service;

import by.lav.car.rental.dao.UsersDao;
import by.lav.car.rental.dto.OrderDto;
import by.lav.car.rental.entity.UsersEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReportService {

    private static final ReportService INSTANCE = new ReportService();

    private final OrderService orderService = OrderService.getInstance();
    private final UsersDao usersDao = UsersDao.getInstance();

    public String createReport() {
        List<OrderDto> orderDtoList = orderService.findAll();
        StringBuffer report = new StringBuffer();
        for (OrderDto orderDto : orderDtoList) {
            UsersEntity user = usersDao.findById(orderDto.getUserId()).orElseThrow();
            String order = String.format("ORDER ID: %s, USER: %s %s, %s;" + System.lineSeparator(),
                    orderDto.getId(), user.getFirstName(), user.getLastName(), orderDto.getDescription());
            report.append(order);
        }
        return report.toString();
    }

    public static ReportService getInstance() {
        return INSTANCE;
    }
}
