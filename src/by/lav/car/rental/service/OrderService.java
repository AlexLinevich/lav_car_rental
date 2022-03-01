package by.lav.car.rental.service;

import by.lav.car.rental.dao.CarDao;
import by.lav.car.rental.dao.ClientDataDao;
import by.lav.car.rental.dao.OrdersDao;
import by.lav.car.rental.dao.RentalTimeDao;
import by.lav.car.rental.dto.OrderDto;
import by.lav.car.rental.entity.ClientDataEntity;
import by.lav.car.rental.entity.OrderStatus;
import by.lav.car.rental.entity.OrdersEntity;
import by.lav.car.rental.entity.RentalTimeEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

public class OrderService {

    private static final OrderService INSTANCE = new OrderService();

    private final OrdersDao ordersDao = OrdersDao.getInstance();
    private final CarDao carDao = CarDao.getInstance();
    private final RentalTimeDao rentalTimeDao = RentalTimeDao.getInstance();
    private final ClientDataDao clientDataDao = ClientDataDao.getInstance();

    private OrderService() {
    }

    public List<OrderDto> findAll() {
        return ordersDao.findAll().stream()
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
                        .build())
                .collect(toList());
    }

    public List<OrderDto> ordersByUser(Integer userId) {
        var orderDtos = findAll();
        return orderDtos.stream()
                .filter(orderDto -> orderDto.getUserId().equals(userId))
                .collect(toList());
    }

    public void check(OrderDto orderDto) {
        var rentalTimeEntities = rentalTimeDao.findAllByCarId(orderDto.getCarId());
        Integer userId = orderDto.getUserId();
        Optional<Integer> idByUserId = clientDataDao.findIdByUserId(userId);
        Optional<ClientDataEntity> clientDataEntity = clientDataDao.findById(idByUserId.orElseThrow());
        LocalDate dlExpirationDay = clientDataEntity.orElseThrow().getDlExpirationDay();
        double clientCreditAmount = clientDataEntity.orElseThrow().getCreditAmount().doubleValue();
        LocalDateTime beginTime = orderDto.getBeginTime();
        LocalDateTime endTime = orderDto.getEndTime();
        BigDecimal dayPrice = carDao.findById(orderDto.getCarId()).orElseThrow().getCarCategory().getDayPrice();

        double endPrice = calculateEndPrice(dayPrice, beginTime, endTime);

        if (orderDto.getStatus().equals(OrderStatus.PROCESSING)) {
            String timeMassage = "";
            if (isCarReserved(beginTime, endTime, rentalTimeEntities)) {
                timeMassage = " Chosen time is not available. Choose other time or other car. ";
            }
            String moneyMassage = "";
            if (isMoneyNotEnough(endPrice, clientCreditAmount)) {
                moneyMassage = " Not enough money in your account. Add money and try again. ";
            }
            String dlExpirationMassage = "";
            if (isDlExpirationNotValid(endTime, dlExpirationDay)) {
                dlExpirationMassage = " Your driving license is not valid during car using time. " +
                        "Add valid driver license in your account. ";
            }
            String message = " Have a nice trip! ";
            OrderStatus orderStatus = OrderStatus.ACCEPTED;
            if (isCarReserved(beginTime, endTime, rentalTimeEntities)
                    || isMoneyNotEnough(endPrice, clientCreditAmount)
                    || isDlExpirationNotValid(endTime, dlExpirationDay)) {
                orderStatus = OrderStatus.CANCELED;
                message = String.format("%s %s %s", timeMassage, moneyMassage, dlExpirationMassage);
            } else {
                ClientDataEntity clientDataToUpdate = ClientDataEntity.builder()
                        .id(idByUserId.orElseThrow())
                        .userId(userId)
                        .driverLicenceNo(clientDataEntity.orElseThrow().getDriverLicenceNo())
                        .dlExpirationDay(dlExpirationDay)
                        .creditAmount(BigDecimal.valueOf(clientCreditAmount - endPrice))
                        .build();
                clientDataDao.update(clientDataToUpdate);

                RentalTimeEntity rentalTimeEntity = RentalTimeEntity.builder()
                        .carId(orderDto.getCarId())
                        .orderId(orderDto.getId())
                        .beginTime(beginTime)
                        .endTime(endTime)
                        .build();
                rentalTimeDao.save(rentalTimeEntity);

                message = message + "TOTAL PRICE:" + endPrice + "$";
            }
            Optional<OrdersEntity> order = ordersDao.findById(orderDto.getId());
            if (order.isPresent()) {
                OrdersEntity ordersToUpdate = OrdersEntity.builder()
                        .id(orderDto.getId())
                        .carId(orderDto.getCarId())
                        .userId(orderDto.getUserId())
                        .beginTime(orderDto.getBeginTime())
                        .endTime(orderDto.getEndTime())
                        .status(orderStatus)
                        .message(message)
                        .build();
                ordersDao.update(ordersToUpdate);
            }
        }
    }

    public static OrderService getInstance() {
        return INSTANCE;
    }

    private boolean isDlExpirationNotValid(LocalDateTime endTime, LocalDate dlExpirationDay) {
        return dlExpirationDay.isBefore(endTime.toLocalDate());
    }

    private boolean isMoneyNotEnough(double endPrice, double clientCreditAmount) {
        return endPrice > clientCreditAmount;
    }

    private boolean isCarReserved(LocalDateTime beginTime, LocalDateTime endTime, List<RentalTimeEntity> rentalTimeEntities) {
        boolean result = false;
        for (RentalTimeEntity rentalTimeEntity : rentalTimeEntities) {
            if ((beginTime.isAfter(rentalTimeEntity.getBeginTime()) && beginTime.isBefore(rentalTimeEntity.getEndTime())) ||
                    (endTime.isAfter(rentalTimeEntity.getBeginTime()) && endTime.isBefore(rentalTimeEntity.getEndTime()))) {
                result = true;
            }
        }
        return result;
    }

    private double calculateEndPrice(BigDecimal dayPrice, LocalDateTime beginTime, LocalDateTime endTime) {
        double result = 0;
        long days = ChronoUnit.DAYS.between(beginTime, endTime);
        return result = days * dayPrice.doubleValue();
    }
}
