package by.lav.car.rental.entity;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class ClientDataEntity {

    private Integer id;
    private Integer userId;
    private String driverLicenceNo;
    private LocalDate dlExpirationDay;
    private BigDecimal creditAmount;
}
